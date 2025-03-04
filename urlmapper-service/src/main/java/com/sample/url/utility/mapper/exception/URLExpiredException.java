package com.sample.url.utility.mapper.exception;

import lombok.Data;

@Data
public class URLExpiredException extends RuntimeException {
    private String exception;
    public URLExpiredException(String exception){
        super(exception);
    }
}
