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

import java.text.Normalizer;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private final CategoryRepository categoryRepository;

    private static final Pattern NONLATIN = Pattern.compile("[^\\w-]");
    private static final Pattern WHITESPACE = Pattern.compile("[\\s]");

    private final CategoryMapper mapper;

    public static String toSlug(String input) {
        String nowhitespace = WHITESPACE.matcher(input).replaceAll("-");
        String normalized = Normalizer.normalize(nowhitespace, Normalizer.Form.NFD);
        String slug = NONLATIN.matcher(normalized).replaceAll("");
        return slug.toLowerCase(Locale.ENGLISH);
    }

    @Override
    public ResponseEntity<CategoryEntity> createCategory(CategoryRequest categoryRequest) {
        if(!categoryRequest.getParent().isEmpty()) {
            Optional<CategoryEntity> parentCatgory = categoryRepository.findById(categoryRequest.getParent());
            if(parentCatgory.isEmpty()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        CategoryEntity categoryEntity = CategoryEntity.builder()
                .name(categoryRequest.getName())
                .slug(toSlug(categoryRequest.getName()))
                .thumbnail(categoryRequest.getThumbnail())
                .parent(categoryRequest.getParent())
                .build();
        return new ResponseEntity<>(categoryRepository.save(categoryEntity), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<CategoryResponse>> getAll() {
        return ResponseEntity.ok(categoryRepository.findAll().stream().map(mapper::toResponse).toList());
    }

    @Override
    public ResponseEntity<CategoryEntity> updateCategory(String categoryId, CategoryRequest request) {
        Optional<CategoryEntity> categoryData = categoryRepository.findById(categoryId);
        if (categoryData.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if(request.getName() != null)
            categoryData.get().setName(request.getName());
        categoryData.get().setSlug(toSlug(request.getName()));
        if(request.getThumbnail() != null)
            categoryData.get().setThumbnail(request.getThumbnail());
        if(request.getParent() != null) {
            if(!request.getParent().isEmpty()) {
                Optional<CategoryEntity> parentCatgory = categoryRepository.findById(request.getParent());
                if(parentCatgory.isEmpty()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            } else {
                categoryData.get().setParent(request.getParent());
            }
        }
        return new ResponseEntity<>(categoryRepository.save(categoryData.get()), HttpStatus.OK);
    }

    @Override
    public ResponseEntity deleteCategory(String categoryId) {
        Optional<CategoryEntity> categoryData = categoryRepository.findById(categoryId);
        if (categoryData.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        categoryRepository.deleteById(categoryId);
        return new ResponseEntity(HttpStatus.OK);
    }
}
