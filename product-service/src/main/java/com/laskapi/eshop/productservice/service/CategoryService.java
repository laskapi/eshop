package com.laskapi.eshop.productservice.service;

import com.laskapi.eshop.productservice.entity.Category;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface CategoryService {

    List<Category> getCategories();

    Category getCategory(long id);

    Category addCategory(Category category);

    Category updateCategory(Category category);

    void deleteCategory(Long id);
}

