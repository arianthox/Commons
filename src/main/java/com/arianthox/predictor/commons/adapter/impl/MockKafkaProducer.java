package com.arianthox.predictor.commons.adapter.impl;


import akka.Done;
import com.arianthox.predictor.commons.adapter.KafkaProducer;
import lombok.extern.java.Log;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
@Log
@Profile("mock")
public class MockKafkaProducer implements KafkaProducer {



    @Override
    public void send(final Object obj){
        send(obj,done -> {
            log.fine("Message sent to Mock Broker");
        });
    }

    @Override
    public void send(final Object obj, Consumer<? super Done> action){
        action.accept(Done.done());
    }

    @Override
    public void send(String topicId, String strMessage, Consumer<? super Done> action) {
        action.accept(Done.done());
    }

    @Override
    public void send(String topicId, Object obj, Consumer<? super Done> action) {
        action.accept(Done.done());
    }

}
