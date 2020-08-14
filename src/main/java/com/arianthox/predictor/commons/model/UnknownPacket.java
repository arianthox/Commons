package com.arianthox.predictor.commons.model;

import java.util.HashMap;
import java.util.logging.Level;

public class UnknownPacket extends ParentPacket<UnknownPacket,UnknownPacket>  implements Packet{

    @Override
    public HashMap<String, Object> toHashMap() {
        HashMap<String, Object> map = new HashMap<>();
        return map;
    }

    @Override
    public Level getLogLevel(){
        return Level.OFF;
    }
}
