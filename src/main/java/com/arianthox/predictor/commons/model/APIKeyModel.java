package com.arianthox.predictor.commons.model;


import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@EqualsAndHashCode(callSuper = false)
public class APIKeyModel extends DataMapperModel<APIKeyModel> {

    private String apiKey;
    private String secret;
    private String subject;

}
