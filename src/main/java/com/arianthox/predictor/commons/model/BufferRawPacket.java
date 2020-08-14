package com.arianthox.predictor.commons.model;


import com.arianthox.predictor.commons.persistence.elastic.domain.BufferRawData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper=false)
public class BufferRawPacket extends ParentPacket<BufferRawData,BufferRawPacket>  implements Packet {

    public int[] bufferRawEeg;

}
