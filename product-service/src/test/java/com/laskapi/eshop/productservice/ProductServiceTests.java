package com.laskapi.eshop.productservice;

import com.laskapi.eshop.productservice.dto.ProductDto;
import com.laskapi.eshop.productservice.entity.Category;
import com.laskapi.eshop.productservice.entity.Product;
import com.laskapi.eshop.productservice.repository.CategoryRespository;
import com.laskapi.eshop.productservice.repository.ProductRepository;
import com.laskapi.eshop.productservice.service.ProductServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ProductServiceTests {


    @Mock
    ProductRepository productRepository;
    @Mock
    CategoryRespository categoryRespository;

    @InjectMocks
    ProductServiceImpl productService;

    Category category;
    ProductDto productDto;
    Product product;
    @BeforeEach
    void setUp() {

        category=Category.builder().name("test category").parent_id(0).build();

        product=Product.builder().id(1L).name("test product").price(20).quantity(40).category(category).build();
        productDto=new ProductDto(product);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void addProduct() {
        given(categoryRespository.findById(1L)).willReturn(Optional.of(category));
        given(productRepository.save(any(Product.class))).willReturn(product);
        assertEquals(product,productService.addProduct(productDto));
    }

    @Test
    void getProductById() {
    }

    @Test
    void getAllProducts() {
    }

    @Test
    void getProductsByCategory() {
    }

    @Test
    void updateProduct() {
    }

    @Test
    void increaseQuantity() {
    }

    @Test
    void reduceQuantity() {
    }


    @Test
    void deleteProductById() {
    }
}