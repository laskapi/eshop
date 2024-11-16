package com.laskapi.eshop.productservice.service.impl;

import com.laskapi.eshop.productservice.dto.ProductDTO;
import com.laskapi.eshop.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class ProductServiceImpl implements ProductService {
    @Override
    public long addProduct() {
        return 0;
    }

    @Override
    public ProductDTO getProductById(long productId) {
        return null;
    }

    @Override
    public void reduceQuantity(long productId, long quantity) {

    }

    @Override
    public void deleteProductById(long productId) {

    }
}
