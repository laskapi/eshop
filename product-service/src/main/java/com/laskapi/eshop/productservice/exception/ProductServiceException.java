package com.laskapi.eshop.productservice.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ProductServiceException extends RuntimeException{
    private HttpStatus httpStatus;
    public ProductServiceException(String message,HttpStatus httpStatus){
        super(message);
        this.httpStatus=httpStatus;
    }
}
