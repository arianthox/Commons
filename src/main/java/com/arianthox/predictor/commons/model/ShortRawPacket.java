package com.arianthox.predictor.commons.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.logging.Level;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper=false)
public class ShortRawPacket extends ParentPacket<ShortRawPacket,ShortRawPacket>  implements Packet {

    private List<Short> raw;

    @Override
    public Level getLogLevel(){
        return Level.FINE;
    }
}
