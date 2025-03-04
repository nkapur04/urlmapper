package com.sample.url.utility.mapper.exception;

import lombok.Data;

@Data
public class SyntaxURLException extends RuntimeException{

    private String exception;
    public SyntaxURLException(String exception){
        super(exception);
    }

}
