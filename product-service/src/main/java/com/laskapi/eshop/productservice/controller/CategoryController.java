package com.laskapi.eshop.productservice.controller;

import com.laskapi.eshop.productservice.entity.Category;
import com.laskapi.eshop.productservice.exception.CustomServiceException;
import com.laskapi.eshop.productservice.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController

@RequestMapping(path = "/categories", produces="application/json" )
public class CategoryController {
    @Autowired
    CategoryService categoryService;


    @GetMapping("/")
    public ResponseEntity<List<Category>> getCategories() {
        return ResponseEntity.ok(categoryService.getCategories());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategory(@PathVariable long id) {
        return ResponseEntity.ok(categoryService.getCategory(id));
    }

    @PostMapping
    public ResponseEntity<Category> addCategory(@RequestBody Category category) {
        return categoryService.addCategory(category);
    }

    @PutMapping("/")
    public ResponseEntity<Category> updateCategory(@RequestBody Category category) {
        return ResponseEntity.ok(categoryService.updateCategory(category));
    }

    @DeleteMapping(path = {"/", "/{id}"})
    public ResponseEntity deleteCategory(@PathVariable(required = false) Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }


    @ExceptionHandler(CustomServiceException.class)
    public ResponseEntity<String> handleException(CustomServiceException exception) {
        return new ResponseEntity<>(exception.getMessage(), exception.getHttpStatus());
    }
}
