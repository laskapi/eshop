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
        if (category.getParent_id() == 0
                || categoryRepo.existsById(category.getParent_id())) {
            return categoryRepo.save(category);
        }
        throw new CustomServiceException("Must be root or have parent element",HttpStatus.NOT_ACCEPTABLE);
    }

    @Override
    public Category updateCategory(Category category) {

       return categoryRepo.findById(category.getId()).map(oldCat->
                {
                    category.setId(oldCat.getId());
                    return categoryRepo.save(category);
                }).orElseGet(()->addCategory(category));
    }

    @Override
    public void deleteCategory(Long id) {
            categoryRepo.deleteById(id);
    }
}
