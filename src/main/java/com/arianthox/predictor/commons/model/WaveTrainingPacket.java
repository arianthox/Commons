package com.arianthox.predictor.commons.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class WaveTrainingPacket {
    private WavePacket header;
    private List<WavePacket> buffer;
}
