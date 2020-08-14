package com.arianthox.predictor.commons.model;

import lombok.*;

import java.util.logging.Level;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class RawPacket extends ParentPacket<RawPacket,RawPacket>  implements Packet {

    private int rawEeg;

    public Level getLogLevel(){
        return Level.FINEST;
    }

}
