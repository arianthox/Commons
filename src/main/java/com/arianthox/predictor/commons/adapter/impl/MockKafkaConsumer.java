package com.arianthox.predictor.commons.adapter.impl;


import akka.Done;
import akka.japi.function.Function;
import com.arianthox.predictor.commons.adapter.KafkaConsumer;
import com.arianthox.predictor.commons.model.ConsumerID;
import com.arianthox.predictor.commons.model.TopicID;
import lombok.extern.java.Log;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletionStage;

@Component
@Log
@Profile("mock")
public class MockKafkaConsumer implements KafkaConsumer {

    @Override
    public void consume(){

    }

    @Override
    public void consume(Function<ConsumerRecord<String, String>, CompletionStage<Done>> process) {

    }

    @Override
    public void consume(TopicID topicId, Function<ConsumerRecord<String, String>, CompletionStage<Done>> process) {

    }

    @Override
    public void consume(ConsumerID consumerId, TopicID topicId, Function<ConsumerRecord<String, String>, CompletionStage<Done>> process) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public Status getStatus() {
        return Status.STOPPED;
    }


}
