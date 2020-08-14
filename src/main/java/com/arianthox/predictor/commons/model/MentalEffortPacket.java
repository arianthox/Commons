package com.arianthox.predictor.commons.model;

import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class MentalEffortPacket extends ParentPacket<MentalEffortPacket,MentalEffortPacket>  implements Packet{

    private double mentalEffort;

}
