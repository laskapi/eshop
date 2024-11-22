package com.laskapi.eshop.productservice.controller;

import com.laskapi.eshop.productservice.dto.ProductDto;
import com.laskapi.eshop.productservice.exception.ProductServiceException;
import com.laskapi.eshop.productservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping("/")
    public String getMe(){
        return "It's me";
    }
    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable("id") long id) {
        ProductDto response = ProductDto.builder().price(id).name("Testable product").build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ExceptionHandler(ProductServiceException.class)
    public ResponseEntity<String> handleException(ProductServiceException exception){
        return new ResponseEntity<>(exception.getMessage(),exception.getHttpStatus());
    }
}
