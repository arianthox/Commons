package com.arianthox.predictor.commons.model;

import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class FamiliarityPacket extends ParentPacket<FamiliarityPacket,FamiliarityPacket>  implements Packet{

    private double familiarity;

}
