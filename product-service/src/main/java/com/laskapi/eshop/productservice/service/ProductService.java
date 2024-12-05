package com.laskapi.eshop.productservice.service;

import com.laskapi.eshop.productservice.dto.ProductDto;
import com.laskapi.eshop.productservice.entity.Product;

import java.util.List;

public interface ProductService {
    Product addProduct(ProductDto product);

    ProductDto getProductById(long productId);

    List<ProductDto> getAllProducts();

    List<ProductDto> getProductsByCategory(String category);

    long increaseQuantity(long productId, long quantity);

    long reduceQuantity(long productId, long quantity);

    void deleteProductById(long productId);

    Product updateProduct(long id, ProductDto productDto);
}

