package com.laskapi.eshop.productservice.repository;

import com.laskapi.eshop.productservice.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface CategoryRespository extends JpaRepository<Category, Long> {
    Category findByName(String name);
}
