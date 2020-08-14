package com.arianthox.predictor.commons.adapter.impl;


import akka.Done;
import akka.actor.CoordinatedShutdown;
import akka.actor.typed.ActorSystem;
import akka.actor.typed.javadsl.Behaviors;
import akka.japi.function.Function;
import akka.kafka.ConsumerSettings;
import akka.kafka.ProducerSettings;
import akka.kafka.Subscriptions;
import akka.kafka.javadsl.Consumer;
import com.arianthox.predictor.commons.adapter.util.OffsetStorage;
import com.arianthox.predictor.commons.model.ConsumerID;
import com.arianthox.predictor.commons.model.TopicID;
import com.arianthox.predictor.commons.adapter.KafkaConsumer;
import lombok.extern.java.Log;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.logging.Level;

import static akka.actor.typed.javadsl.Adapter.toClassic;

@Component
@Scope("prototype")
@Log
@Profile("default")
@ConditionalOnProperty(name = "spring.kafka.template.default-topic")
public class StreamKafkaConsumer implements KafkaConsumer {

    private static transient ProducerSettings<String, String> kafkaProducerSettings;
    private static transient ActorSystem<Void> system;

    @Value("${spring.kafka.template.default-topic}")
    private transient String topic;

    @Value("${spring.kafka.consumer.group-id}")
    private transient String groupId;

    @Value("${spring.kafka.bootstrap-servers}")
    private transient String bootstrapServers;

    private transient ConsumerSettings<String, String> kafkaConsumerSettings;

    private transient CompletionStage<Object> control;

    private final transient OffsetStorage db;

    private static Status status=Status.STOPPED;

    private transient ConsumerID consumerId;
    private transient TopicID topicId;
    private transient Function<ConsumerRecord<String, String>, CompletionStage<Done>> process;



    public StreamKafkaConsumer(OffsetStorage db) {
        this.db = db;
    }

    @PostConstruct
    private void initialize() {


    }


    @Override
    public void consume() {
        consume(param -> {
            log.info("Default Processor");
            return CompletableFuture.completedFuture(Done.getInstance());
        });
    }

    public void consume(final Function<ConsumerRecord<String, String>, CompletionStage<Done>> process) {
        consume(TopicID.DEFAULT, process);
    }

    public void consume(final TopicID topicId, final Function<ConsumerRecord<String, String>, CompletionStage<Done>> process) {
        consume(ConsumerID.DEFAULT, topicId, process);
    }

    public void consume(final ConsumerID consumerId, final TopicID topicId, final Function<ConsumerRecord<String, String>, CompletionStage<Done>> process) {
        this.consumerId =consumerId;
        this.topicId= topicId;
        this.process = process;

        system = ActorSystem.create(Behaviors.empty(), "kafka-system-consumer");

        kafkaConsumerSettings =
                ConsumerSettings.create(toClassic(system), new StringDeserializer(), new StringDeserializer())
                        .withBootstrapServers(bootstrapServers)
                        .withGroupId(groupId)
                        .withProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest")
                        .withProperty(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true")
                        .withProperty(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "5000");

        db.setProcessor(process);


        control = db.loadOffset(consumerId.name(), topicId.toString())
                .thenApply(
                        fromOffset -> {
                            log.log(Level.INFO, "Starting Offset:" + fromOffset);
                            return Consumer.plainSource(
                                    kafkaConsumerSettings,
                                    Subscriptions.assignmentWithOffset(
                                            new TopicPartition(topicId.toString(), 0), fromOffset))
                                    .map(db::process)
                                    .run(system);
                        });
        status= Status.RUNNING;
        control.whenComplete((control, throwable) -> log.log(Level.FINE, "Consume Completed"));

    }

    public void pause(){
        if(control!=null) {
            control.thenAccept(c -> destroy());
        }
    }

    @Override
    public void resume() {
        consume(this.consumerId,this.topicId,this.process);
    }

    public Status getStatus(){
        return status;
    }


    @PreDestroy
    private void destroy() {
        CoordinatedShutdown cs = CoordinatedShutdown.get(system);
        cs.run(CoordinatedShutdown.unknownReason());
        status= Status.STOPPED;
    }

}
