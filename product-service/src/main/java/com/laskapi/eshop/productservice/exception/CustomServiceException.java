package com.laskapi.eshop.productservice.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CustomServiceException extends RuntimeException{
    private HttpStatus httpStatus;
    public CustomServiceException(String message, HttpStatus httpStatus){
        super(message);
        this.httpStatus=httpStatus;
    }
}
