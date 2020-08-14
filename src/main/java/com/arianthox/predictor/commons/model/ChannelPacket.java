package com.arianthox.predictor.commons.model;


import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class ChannelPacket extends ParentPacket<ChannelPacket,ChannelPacket> implements Packet {

    private ESensePacket eSense;
    private EEGPowerPacket eegPower;
    private int poorSignalLevel;

}
