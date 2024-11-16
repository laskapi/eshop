package com.laskapi.eshop.productservice.repository;

import com.laskapi.eshop.productservice.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {
}
