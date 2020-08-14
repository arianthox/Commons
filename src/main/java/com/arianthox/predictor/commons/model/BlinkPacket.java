package com.arianthox.predictor.commons.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper=false)
public class BlinkPacket extends ParentPacket<BlinkPacket,BlinkPacket> implements Packet {

    private int blinkStrength;

}
