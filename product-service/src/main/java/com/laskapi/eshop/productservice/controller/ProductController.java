package com.laskapi.eshop.productservice.controller;

import com.laskapi.eshop.productservice.dto.ProductDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController {

    @GetMapping("/")
    public String getMe(){
        return "It's me";
    }
    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable("id") long id) {
        ProductDTO response = ProductDTO.builder().price(id).name("reebok").build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
