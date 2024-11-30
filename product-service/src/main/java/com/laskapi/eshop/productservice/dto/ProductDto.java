package com.laskapi.eshop.productservice.dto;

import com.laskapi.eshop.productservice.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ProductDto {
    public ProductDto(Product product) {
        this(product.getName(), product.getCategory().getId(), product.getPrice(), product.getQuantity());
    }

    private String name;
    private long category_id;
    private long price;
    private long quantity;


}
