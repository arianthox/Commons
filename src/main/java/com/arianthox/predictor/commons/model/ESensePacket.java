package com.arianthox.predictor.commons.model;


import lombok.*;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class ESensePacket extends ParentPacket<ESensePacket,ESensePacket> implements Packet {

    private int attention;
    private int meditation;

}
