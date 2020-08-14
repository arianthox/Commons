package com.arianthox.predictor.commons.model;

import com.arianthox.predictor.commons.adapter.PacketAdapter;
import com.google.gson.annotations.JsonAdapter;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WavePacket<T extends Packet> {

    private String deviceId;
    private String sessionId;

    @JsonAdapter(PacketAdapter.class)
    private T packet;

}
