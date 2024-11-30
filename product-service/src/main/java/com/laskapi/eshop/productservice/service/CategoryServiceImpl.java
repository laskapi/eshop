package com.laskapi.eshop.productservice.service;

import com.laskapi.eshop.productservice.entity.Category;
import com.laskapi.eshop.productservice.exception.CustomServiceException;
import com.laskapi.eshop.productservice.repository.CategoryRespository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRespository categoryRepo;

    @Override
    public List<Category> getCategories() {
        return categoryRepo.findAll(Sort.by("id"));
    }

    @Override
    public Category getCategory(long id) {
        return categoryRepo.findById(id).orElseThrow(() -> new CustomServiceException("no such category", HttpStatus.NOT_FOUND));
    }

    @Override
    public Category addCategory(Category category) {
        return categoryRepo.save(category);
    }

    @Override
    public Category updateCategory(Category category) {
        if (!categoryRepo.existsById(category.getId())) {
            throw new CustomServiceException("Category doesn't exist", HttpStatus.NOT_FOUND);
        }
        return categoryRepo.save(category);

    }

    @Override
    public void deleteCategory(Long id) {
        if (id == null) {
            categoryRepo.deleteAll();
        } else {
            categoryRepo.deleteById(id);
        }
    }
}
