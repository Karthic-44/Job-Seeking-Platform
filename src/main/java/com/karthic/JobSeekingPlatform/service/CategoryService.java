package com.karthic.JobSeekingPlatform.service;

import com.karthic.JobSeekingPlatform.payload.CategoryDTO;
import com.karthic.JobSeekingPlatform.payload.CategoryResponse;

public interface CategoryService {
    CategoryResponse getAllCategories(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);
    CategoryDTO createCategory(CategoryDTO categoryDTO);
    CategoryDTO deleteCategory(Long categoryId);

    CategoryDTO updateCategory(CategoryDTO categoryDTO, Long categoryId);
}
