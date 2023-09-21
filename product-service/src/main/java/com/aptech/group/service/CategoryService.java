package com.aptech.group.service;

import com.aptech.group.dto.category.CategoryRequest;
import com.aptech.group.dto.category.CategoryResponse;
import com.aptech.group.model.CategoryEntity;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CategoryService {
    ResponseEntity<CategoryEntity> createCategory(CategoryRequest categoryRequest);
    ResponseEntity<List<CategoryResponse>> getAll();
    ResponseEntity<CategoryEntity> updateCategory(String categoryId, CategoryRequest categoryRequest);
    ResponseEntity deleteCategory(String categoryId);
}
