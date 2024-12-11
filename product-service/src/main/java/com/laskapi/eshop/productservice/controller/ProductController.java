package com.laskapi.eshop.productservice.controller;

import com.laskapi.eshop.productservice.dto.ProductDto;
import com.laskapi.eshop.productservice.entity.Product;
import com.laskapi.eshop.productservice.exception.CustomServiceException;
import com.laskapi.eshop.productservice.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j
@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductDto>> getMe() {
        List<ProductDto> results= productService.getAllProducts();
        return ResponseEntity.ok(results);
    }

    @PostMapping
    public ResponseEntity<Product> addProduct(@RequestBody ProductDto product) {
      log.info("Got add new product request");
        return new ResponseEntity<>(productService.addProduct(product), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable long id) {
        ProductDto product = productService.getProductById(id);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable long id, @RequestBody ProductDto productDto){
        return ResponseEntity.ok(productService.updateProduct(id,productDto));
}

    @PatchMapping("/{id}")
    public ResponseEntity<Long> changeQuantity(@PathVariable("id") long productId, @RequestParam long quantity,
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

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable("id") long productId){
        productService.deleteProductById(productId);
        return new ResponseEntity<>("",HttpStatus.NO_CONTENT);
    }



    @ExceptionHandler(CustomServiceException.class)
    public ResponseEntity<String> handleException(CustomServiceException exception) {
        return new ResponseEntity<>(exception.getMessage(), exception.getHttpStatus());
    }
}
