package com.laskapi.eshop.productservice.dto;

import lombok.Builder;
import lombok.Data;

@Data

@Builder
public class ProductDTO {
    private String name;
    private long price;
    private long quantity;
}
