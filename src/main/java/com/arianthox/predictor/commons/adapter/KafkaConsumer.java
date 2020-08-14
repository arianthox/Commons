package com.arianthox.predictor.commons.adapter;

import akka.Done;
import akka.japi.function.Function;
import com.arianthox.predictor.commons.model.ConsumerID;
import com.arianthox.predictor.commons.model.TopicID;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.util.concurrent.CompletionStage;

public interface KafkaConsumer {
    void consume();
    void consume(Function<ConsumerRecord<String, String>, CompletionStage<Done>> process);
    void consume(TopicID topicId, Function<ConsumerRecord<String, String>, CompletionStage<Done>> process);
    void consume(ConsumerID consumerId, TopicID topicId, Function<ConsumerRecord<String, String>, CompletionStage<Done>> process);
    void pause();
    void resume();
    Status getStatus();

    enum  Status{
        STOPPED,
        RUNNING;
    }
}
