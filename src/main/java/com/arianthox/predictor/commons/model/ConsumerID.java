package com.arianthox.predictor.commons.model;

public enum ConsumerID{
    DEFAULT,
    CORE_ENGINE,
    BW_MATCHER,
    BW_MATCHER_TRAINER,
    THINK_GEAR,
    THINK_GEAR_FX,
    ;


    @Override
    public String toString() {
        return this.name();
    }
}
