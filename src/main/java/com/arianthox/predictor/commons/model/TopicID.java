package com.arianthox.predictor.commons.model;

import com.arianthox.predictor.commons.utils.CommonUtil;

public enum TopicID {
    DEFAULT("default"),
    CHANNEL("%s_channel"),
    MATCHER_TRAINER(),
    MATCHER(),
    DRAWS(),
    MATCHER_RESULT(),
    THINK_GEAR_READER();

    private String topic;
    TopicID(String prefix){
        this.topic = prefix;
    }
    TopicID(){
        this.topic= CommonUtil.formatTopicName(this.name().toLowerCase());
    }
    public TopicID from(Class<?> packetClass){
        this.topic = CommonUtil.formatTopicName(String.format(topic, CommonUtil.getTopicName(packetClass)));
        return this;
    }

    @Override
    public String toString() {
        return this.topic;
    }
}
