package com.aptech.group.service.impl;

import com.aptech.group.dto.category.CategoryRequest;
import com.aptech.group.dto.category.CategoryResponse;
import com.aptech.group.mapstruct.CategoryMapper;
import com.aptech.group.model.CategoryEntity;
import com.aptech.group.repository.CategoryRepository;
import com.aptech.group.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private final CategoryRepository categoryRepository;

    private final CategoryMapper mapper;

    @Override
    public ResponseEntity<CategoryEntity> createCategory(CategoryRequest categoryRequest) {
        CategoryEntity categoryEntity = mapper.toEntity(categoryRequest);
        return new ResponseEntity<>(categoryRepository.save(categoryEntity), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<CategoryResponse>> getAll() {
        return ResponseEntity.ok(categoryRepository.findAll().stream().map(mapper::toResponse).toList());
    }

    @Override
    public ResponseEntity<CategoryEntity> updateCategory(CategoryRequest categoryRequest, String categorySlug) {
        Optional<CategoryEntity> categoryData = categoryRepository.findBySlug(categorySlug);
        return new ResponseEntity<>(categoryRepository.save(categoryData.get()), HttpStatus.OK);
    }

    @Override
    public ResponseEntity deleteCategory(String categoryId) {
        categoryRepository.deleteById(categoryId);
        return new ResponseEntity(HttpStatus.OK);
    }
}
