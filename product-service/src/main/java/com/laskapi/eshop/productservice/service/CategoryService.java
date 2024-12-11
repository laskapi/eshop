package com.laskapi.eshop.productservice.service;

import com.laskapi.eshop.productservice.entity.Category;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CategoryService {

    List<Category> getCategories();

    Category getCategory(long id);

    ResponseEntity<Category> addCategory(Category category);

    Category updateCategory(Category category);

    void deleteCategory(Long id);
}

