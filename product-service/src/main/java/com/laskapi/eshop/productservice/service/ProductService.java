package com.laskapi.eshop.productservice.service;

import com.laskapi.eshop.productservice.dto.ProductDTO;

public interface ProductService {
    long addProduct();

    ProductDTO getProductById(long productId);

    void reduceQuantity(long productId, long quantity);

    void deleteProductById(long productId);
}
