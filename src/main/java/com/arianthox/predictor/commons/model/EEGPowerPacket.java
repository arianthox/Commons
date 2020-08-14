package com.arianthox.predictor.commons.model;


import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class EEGPowerPacket extends ParentPacket<EEGPowerPacket,EEGPowerPacket>  implements Packet {

    private long delta, theta, lowAlpha, highAlpha, lowBeta, highBeta, lowGamma, highGamma;

}
