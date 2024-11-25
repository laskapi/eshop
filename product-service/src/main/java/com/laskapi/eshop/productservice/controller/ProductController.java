package com.laskapi.eshop.productservice.controller;

import com.laskapi.eshop.productservice.dto.ProductDto;
import com.laskapi.eshop.productservice.exception.ProductServiceException;
import com.laskapi.eshop.productservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products/")
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping
    public String getMe() {
        return "How can I help You?";
    }

    @PostMapping
    public ResponseEntity<Long> addProduct(@RequestBody ProductDto product) {
        long productId = productService.addProduct(product);
        return new ResponseEntity<>(productId, HttpStatus.CREATED);
    }
    @GetMapping("{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable long id) {
        ProductDto product = productService.getProductById(id);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PatchMapping("{id}")
    public ResponseEntity<Long> increaseQuantity(@PathVariable("id") long productId, @RequestParam long quantity,
                                                 @RequestParam(defaultValue = "true") boolean increase) {
        long resultQuantity;
        if(increase) {
            resultQuantity = productService.increaseQuantity(productId, quantity);
        }
        else{
            resultQuantity = productService.reduceQuantity(productId, quantity);
        }
        return ResponseEntity.ok(resultQuantity);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable("id") long productId){
        productService.deleteProductById(productId);
        return new ResponseEntity<>("",HttpStatus.NO_CONTENT);
    }



    @ExceptionHandler(ProductServiceException.class)
    public ResponseEntity<String> handleException(ProductServiceException exception) {
        return new ResponseEntity<>(exception.getMessage(), exception.getHttpStatus());
    }
}
