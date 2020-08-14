package com.arianthox.predictor.commons.persistence.elastic.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "pattern_file_data")
public class PatternFileData implements Serializable {

    private static final long serialVersionUID = 2020128099084761251L;

    @Id
    private String name;

    private String type;

    private String data;

}
