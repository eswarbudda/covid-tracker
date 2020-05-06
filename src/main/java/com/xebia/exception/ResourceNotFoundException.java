package com.xebia.exception;

import lombok.Getter;

@Getter
public class ResourceNotFoundException extends RuntimeException {
    private String message;
    public ResourceNotFoundException(String msg){
        super(msg);
    }
}
