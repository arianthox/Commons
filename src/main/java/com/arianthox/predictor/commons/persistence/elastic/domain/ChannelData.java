package com.arianthox.predictor.commons.persistence.elastic.domain;



import com.arianthox.predictor.commons.model.EEGPowerPacket;
import com.arianthox.predictor.commons.model.ESensePacket;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "channel_data")
public class ChannelData implements Serializable {

    private static final long serialVersionUID = 2020128099084761251L;

    @Id
    private String id;

    @NotBlank(message = "Device Id is mandatory")
    private String deviceId;

    private String sessionId;

    private ESensePacket eSense;
    private EEGPowerPacket eegPower;
    private int poorSignalLevel;

}
