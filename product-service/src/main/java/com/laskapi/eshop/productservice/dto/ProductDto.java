package com.laskapi.eshop.productservice.dto;

import com.laskapi.eshop.productservice.entity.Product;
import lombok.Builder;
import lombok.Data;

@Data

@Builder
public class ProductDto {
    public ProductDto(Product product) {
        this(product.getProductName(), product.getPrice(), product.getQuantity());
    }

    public ProductDto(String productName, long price, long quantity) {
        this.name = productName;
        this.price = price;
        this.quantity = quantity;
    }

    private String name;
    private long price;
    private long quantity;


}
