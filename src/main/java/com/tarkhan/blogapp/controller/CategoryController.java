package com.tarkhan.blogapp.controller;

import com.tarkhan.blogapp.constants.Constants;
import com.tarkhan.blogapp.model.ResponseModel;
import com.tarkhan.blogapp.model.category.AddCategoryDto;
import com.tarkhan.blogapp.model.category.GetCategoryDto;
import com.tarkhan.blogapp.model.category.UpdateCategoryDto;
import com.tarkhan.blogapp.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/{id}")
    public ResponseEntity<GetCategoryDto> getCategory(
            @Valid @PathVariable("id") Long id
    ) {
        GetCategoryDto category = categoryService.getCategoryById(id);
        return ResponseEntity.status(HttpStatus.OK).body(category);
    }

    @GetMapping("/by-name")
    public ResponseEntity<GetCategoryDto> getCategoryByName(
            @Valid @RequestParam String name
    ) {
        GetCategoryDto category = categoryService.getCategoryByName(name);
        return ResponseEntity.status(HttpStatus.OK).body(category);
    }

    @GetMapping
    public ResponseEntity<Page<GetCategoryDto>> getAllCategories(
            @RequestParam int page,
            @RequestParam int size
    ) {
        Page<GetCategoryDto> categories = categoryService.getAllCategories(page, size);
        return ResponseEntity.status(HttpStatus.OK).body(categories);
    }

    @GetMapping("/search")
    public ResponseEntity<List<GetCategoryDto>> getAllCategoriesByName(
            @Valid @RequestParam String categoryName
    ) {
        List<GetCategoryDto> categories = categoryService.getCategoriesSearch(categoryName);
        return ResponseEntity.status(HttpStatus.OK).body(categories);
    }

    @PostMapping
    public ResponseEntity<ResponseModel> addCategory(
            @RequestBody AddCategoryDto category
    ) {
        categoryService.addCategory(category);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ResponseModel(
                        Constants.STATUS_201,
                        Constants.MESSAGE_201
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseModel> deleteCategory(
            @PathVariable("id") Long id
    ) {
        categoryService.deleteCategory(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(new ResponseModel(
                        Constants.STATUS_200,
                        Constants.MESSAGE_200
                ));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseModel> updateCategory(
            @PathVariable("id") Long id,
            @RequestBody UpdateCategoryDto category
    ) {
        categoryService.updateCategory(category, id);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseModel(
                Constants.STATUS_201,
                Constants.MESSAGE_UPDATE_SUCCESSFUL
        ));
    }
}
