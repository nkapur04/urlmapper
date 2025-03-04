package com.sample.url.utility.mapper.exception;

import lombok.Data;

@Data
public class DataBaseException extends RuntimeException {

    private String exception;
    public DataBaseException(String exception) {
        super(exception);
    }
}
