package com.arianthox.predictor.commons.adapter.impl;


import akka.Done;
import akka.actor.CoordinatedShutdown;
import akka.actor.typed.ActorSystem;
import akka.actor.typed.javadsl.Behaviors;
import akka.kafka.ProducerSettings;
import com.arianthox.predictor.commons.model.TopicID;
import com.arianthox.predictor.commons.adapter.KafkaProducer;
import com.google.gson.Gson;
import com.typesafe.config.Config;
import lombok.extern.java.Log;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.function.Consumer;

@Component
@Log
@Profile({"default","standalone"})
@ConditionalOnProperty(name="spring.kafka.template.default-topic")
public class StreamKafkaProducer implements KafkaProducer {

    private static transient ProducerSettings<String, String> kafkaProducerSettings;
    private static transient ActorSystem<Void> system;
    private static transient org.apache.kafka.clients.producer.Producer<String, String> kafkaProducer;

    @Value("${spring.kafka.template.default-topic}")
    private transient String topic;

    @Value("${spring.kafka.bootstrap-servers}")
    private transient String bootstrapServers;

    private final Gson gson;

    public StreamKafkaProducer(Gson gson) {
        this.gson = gson;
    }

    @PostConstruct
    private void initialize(){

        system = ActorSystem.create(Behaviors.empty(), "kafka-system-producer");

        final Config config = system.settings().config().getConfig("akka.kafka.producer");
        kafkaProducerSettings =
                ProducerSettings.create(config, new StringSerializer(), new StringSerializer())
                        .withBootstrapServers(bootstrapServers);
        kafkaProducer =
                kafkaProducerSettings.createKafkaProducer();
    }

    private String toJson(Object obj) {
        return gson.toJson(obj);
    }

    @Override
    public void send(final Object obj){
        send(obj,done -> log.fine("Message sent to broker"));
    }

    @Override
    public void send(final Object obj, Consumer<? super Done> action){
        send(TopicID.DEFAULT.toString(),obj,action);
    }

    @Override
    public void send(String topicId, Object obj, Consumer<? super Done> action) {
        send(topicId,toJson(obj),action);
    }

    @Override
    public void send(String topicId, String str, Consumer<? super Done> action) {
        kafkaProducer.send(new ProducerRecord<>(
                topicId, str),(metadata, exception) -> action.accept(Done.done()));
    }

    @PreDestroy
    private void destroy(){
        CoordinatedShutdown cs = CoordinatedShutdown.get(system);
        cs.run(CoordinatedShutdown.unknownReason());

    }


}
