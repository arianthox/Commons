package com.arianthox.predictor.commons.model;



import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class StatusPacket extends ParentPacket<StatusPacket,StatusPacket>  implements Packet{

    private String status;
    private int poorSignalLevel;

}
