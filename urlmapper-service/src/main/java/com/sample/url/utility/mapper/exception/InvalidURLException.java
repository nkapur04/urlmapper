package com.sample.url.utility.mapper.exception;

import lombok.Data;
@Data
public class InvalidURLException extends RuntimeException{

    private String exception;
    public InvalidURLException(String exception){
        super(exception);
    }

}
