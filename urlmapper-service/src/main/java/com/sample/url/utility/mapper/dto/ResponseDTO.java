package com.sample.url.utility.mapper.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDTO {
    private String url;
    private ErrorDetails errorDetails;
}
