package com.tarkhan.blogapp.service;

import com.tarkhan.blogapp.model.category.AddCategoryDto;
import com.tarkhan.blogapp.model.category.GetCategoryDto;
import com.tarkhan.blogapp.model.category.UpdateCategoryDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CategoryService {
    AddCategoryDto addCategory(AddCategoryDto category);
    GetCategoryDto getCategoryById(Long id);
    void deleteCategory(Long id);
    UpdateCategoryDto updateCategory(UpdateCategoryDto request, Long id);
    Page<GetCategoryDto> getAllCategories(int page, int pageSize);
    GetCategoryDto getCategoryByName(String name);
    List<GetCategoryDto> getCategoriesSearch(String prefix);
}
