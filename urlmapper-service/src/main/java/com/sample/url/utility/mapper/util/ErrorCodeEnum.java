package com.sample.url.utility.mapper.util;

public enum ErrorCodeEnum {
    EXPURL("The shortened URL has expired in the system. Please try to generate again."),
    DBEXP("Some DB Exception has occurred"),
    SHORTURLMAL("The input URL to be shortened is either not valid or not reachable"),
    SHORTURLSYN("The input URL to be shortened is malformed");
    private final String errorCodeDesc;

    ErrorCodeEnum(String errorCodeDesc) {
        this.errorCodeDesc = errorCodeDesc;
    }

    public String value() {
        return this.errorCodeDesc;
    }

}
