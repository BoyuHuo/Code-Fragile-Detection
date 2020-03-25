package com.ocdl.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReturnDto {

    @JsonProperty("data")
    private Object data;

    @JsonProperty("eta")
    private Long eta;


}
