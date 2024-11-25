package com.laskapi.eshop.productservice.service;

import com.laskapi.eshop.productservice.dto.ProductDto;
import com.laskapi.eshop.productservice.entity.Product;
import com.laskapi.eshop.productservice.exception.ProductServiceException;
import com.laskapi.eshop.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;

    @Override
    public long addProduct(ProductDto productDto) {

        Product product = Product.builder()
                .productName(productDto.getName())
                .price(productDto.getPrice())
                .quantity(productDto.getQuantity())
                .build();

        product = repository.save(product);
        log.info("Product %s added".formatted(product.getProductName()));

        return product.getId();
    }

    @Override
    public ProductDto getProductById(long productId) {
        Product product = repository.findById(productId)
                .orElseThrow(() -> new ProductServiceException("No such Element", HttpStatus.NOT_FOUND));
        log.info("Product %s acquired".formatted(product.getProductName()));
        return new ProductDto(product);
    }

    @Override
    public long increaseQuantity(long productId, long quantity) {
        Product product = repository.findById(productId).orElseThrow();
        product.setQuantity(product.getQuantity() + quantity);
        repository.save(product);
        log.info("Product %s quantity successfully increased".formatted(product.getProductName()));
        return product.getQuantity();
    }

    @Override
    public long reduceQuantity(long productId, long quantity) {
        Product product = repository.findById(productId).orElseThrow();
        long oldQuantity = product.getQuantity();
        if (oldQuantity < quantity)
            throw new ProductServiceException("Available quantity too low.", HttpStatus.NOT_ACCEPTABLE);

        product.setQuantity(oldQuantity - quantity);
        repository.save(product);
        log.info("Product %s quantity successfully reduced".formatted(product.getProductName()));
        return product.getQuantity();
    }

    @Override
    public void deleteProductById(long productId) {
        Product product = repository.findById(productId).orElseThrow(() -> new ProductServiceException("Product not " +
                "exists", HttpStatus.NOT_FOUND));
        repository.deleteById(productId);
        log.info("Product %s deleted".formatted(product.getProductName()));
    }
}
