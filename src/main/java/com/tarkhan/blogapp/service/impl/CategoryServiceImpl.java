package com.tarkhan.blogapp.service.impl;

import com.tarkhan.blogapp.entity.Category;
import com.tarkhan.blogapp.exception.BlogApiException;
import com.tarkhan.blogapp.exception.ResourceNotFoundException;
import com.tarkhan.blogapp.model.category.AddCategoryDto;
import com.tarkhan.blogapp.model.category.GetCategoryByPostDto;
import com.tarkhan.blogapp.model.category.GetCategoryDto;
import com.tarkhan.blogapp.model.category.UpdateCategoryDto;
import com.tarkhan.blogapp.model.post.GetPostDto;
import com.tarkhan.blogapp.repository.CategoryRepository;
import com.tarkhan.blogapp.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Override
    public AddCategoryDto addCategory(AddCategoryDto category) {
        // Check if a category with the same name already exists
        boolean exists = categoryRepository.existsByName(category.getName());

        if (exists) {
            throw new BlogApiException("Category name already exists: " + category.getName());
        }

        // Proceed if the category name does not exist
        Category categoryEntity = modelMapper.map(category, Category.class);
        Category savedCategory = categoryRepository.save(categoryEntity);
        return modelMapper.map(savedCategory, AddCategoryDto.class);
    }

    @Override
    public GetCategoryDto getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "Category ID", id));

        GetCategoryDto result = modelMapper.map(category, GetCategoryDto.class);
        return result;
    }

    @Override
    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "Category ID", id));

        categoryRepository.delete(category);
    }

    @Override
    public UpdateCategoryDto updateCategory(UpdateCategoryDto request, Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "Category ID", id));

        category.setName(request.getName());
        category.setDescription(request.getDescription());
        Category savedCategory = categoryRepository.save(category);
        return modelMapper.map(savedCategory, UpdateCategoryDto.class);
    }

    @Override
    public Page<GetCategoryDto> getAllCategories(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<Category> categories = categoryRepository.findAll(pageable);
        return categories.map(cat -> modelMapper.map(cat, GetCategoryDto.class));
    }

    @Override
    public GetCategoryDto getCategoryByName(String name) {
        Category category = categoryRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "Category Name", name));

        return modelMapper.map(category, GetCategoryDto.class);
    }

    @Override
    public List<GetCategoryDto> getCategoriesSearch(String prefix) {
        List<Category> categories = categoryRepository.findAll();
        List<GetCategoryDto> result = categories.stream()
                .filter(cat -> cat.getName().toLowerCase().startsWith(prefix.toLowerCase()))
                .map(cat -> modelMapper.map(cat, GetCategoryDto.class))
                .collect(Collectors.toList());

        return result;
    }

    @Override
    public GetCategoryByPostDto getCategoryByPost(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "Category ID", id));

        GetCategoryByPostDto dto = modelMapper.map(category, GetCategoryByPostDto.class);
        List<GetPostDto> postDtos = category.getPosts()
                .stream().map((post) -> modelMapper.map(post, GetPostDto.class))
                .collect(Collectors.toList());

        dto.setPosts(postDtos);
        return dto;
    }
}
