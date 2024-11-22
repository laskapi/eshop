package com.laskapi.eshop.productservice.service;

import com.laskapi.eshop.productservice.dto.ProductDto;

public interface ProductService {
    long addProduct(ProductDto product);

    ProductDto getProductById(long productId);

    void increaseQuantity(long productId, long quantity);

    void reduceQuantity(long productId, long quantity);

    void deleteProductById(long productId);
}
