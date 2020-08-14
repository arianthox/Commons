package com.arianthox.predictor.commons.model;

import java.util.Collections;
import java.util.logging.Level;

public class ParentPacket<T,R extends Packet> extends DataMapperModel<T> implements Packet{


    @Override
    public String toString() {
        return Collections.singletonList(toHashMap()).toString();
    }

    @Override
    public Level getLogLevel(){
        return Level.INFO;
    }

    @Override
    public WavePacket.WavePacketBuilder<R> toWave(){
        return WavePacket.<R>builder().packet((R) this);
    }

}
