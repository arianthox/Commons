package com.arianthox.predictor.commons.adapter;

import akka.Done;

import java.util.function.Consumer;

public interface KafkaProducer {
    void send(Object obj);

    void send(Object obj, Consumer<? super Done> action);

    void send(String topicId, Object obj, Consumer<? super Done> action);

    void send(String topicId,String strMessage, Consumer<? super Done> action);
}
