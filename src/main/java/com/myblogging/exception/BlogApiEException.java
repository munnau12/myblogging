package com.myblogging.exception;

import org.springframework.http.HttpStatus;

public class BlogApiEException extends RuntimeException{

    private HttpStatus httpStatus;
    private String message;
    public BlogApiEException(HttpStatus httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
        this.message = message;
    }
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
    @Override
    public String getMessage() {
        return message;
    }
}
