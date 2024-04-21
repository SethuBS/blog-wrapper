package com.sethu.blog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFundException extends RuntimeException {

    public ResourceNotFundException(String errorMessage){
        super(errorMessage);
    }
}
