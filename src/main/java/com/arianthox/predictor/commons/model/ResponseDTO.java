package com.arianthox.predictor.commons.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDTO<T> {

    private T content;
    private String message;
    private int statusCode;


}
