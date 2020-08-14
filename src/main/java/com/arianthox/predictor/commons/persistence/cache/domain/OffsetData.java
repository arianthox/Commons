package com.arianthox.predictor.commons.persistence.cache.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.kafka.common.utils.Crc32;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class OffsetData implements Serializable {

    private static final long serialVersionUID = 2020128099084761252L;

    @Id
    @Column
    private Long id;

    @Column
    private String consumerId;

    @Column
    private String topic;

    @Column
    private Long value;

    public static OffsetDataBuilder builder() {
        return new OffsetDataBuilder(){
            @Override
            public OffsetData build() {
                prebuild();
                return super.build();
            }
        };
    }

    public static class OffsetDataBuilder  {
        void prebuild(){
            id = Crc32.crc32(String.format("%s%s", this.consumerId, this.topic).getBytes());
        }
    }


}


