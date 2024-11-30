package com.laskapi.eshop.productservice.repository;

import com.laskapi.eshop.productservice.entity.Category;
import com.laskapi.eshop.productservice.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {
    List<Product> findAllByCategory(Category category);
}
