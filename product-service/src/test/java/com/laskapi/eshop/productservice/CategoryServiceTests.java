package com.laskapi.eshop.productservice;

import com.laskapi.eshop.productservice.entity.Category;
import com.laskapi.eshop.productservice.repository.CategoryRespository;
import com.laskapi.eshop.productservice.service.CategoryServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTests {

    @Mock
    private CategoryRespository categoryRespository;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    private static Category category;

    @BeforeAll
    public static void setup(){
        category= Category.builder().
                id(1L)
                .name("Test category")
                .parent_id(0L)
                .build();
    }

    @Test
    public void getCategories(){
        List<Category> results=new ArrayList<>();
        results.add(category);
        results.add(Category.builder().id(2L).name("drugi").parent_id(1L).build());
        given(categoryRespository.findAll(Sort.by("id"))).willReturn(results);
        List<Category> response=categoryService.getCategories();
        assertThat(response).isEqualTo(results);
    }


    @Test
    public void getCategory(){
        given(categoryRespository.findById(1L)).willReturn(Optional.of(category));
        Category cat=categoryService.getCategory(category.getId());
        assertThat(cat).isNotNull();
    }


    @Test
    void addCategory() {
        given(categoryRespository.save(category)).willReturn(category);
        assertThat(categoryService.addCategory(category)).isEqualTo(category);

    }

    @Test
    void updateCategory() {
        Category cat= Category.builder().id(1L).name("updated").parent_id(1L).build();
        given(categoryRespository.findById(1L)).willReturn(Optional.of(category));
        given(categoryRespository.save(cat)).willReturn(cat);
        Category result=categoryService.updateCategory(cat);
        assertThat(result.getName()).isEqualTo(cat.getName());
        assertThat(result.getParent_id()).isNotEqualTo(category.getParent_id());

    }

    @Test
    void deleteCategory() {

    }
}
