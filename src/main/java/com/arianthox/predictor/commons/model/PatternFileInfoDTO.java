package com.arianthox.predictor.commons.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatternFileInfoDTO {

    public String name;
    public String content;
    public String type;

}
