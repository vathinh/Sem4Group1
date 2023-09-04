package com.aptech.group.controller;

import com.aptech.group.dto.category.CategoryRequest;
import com.aptech.group.dto.category.CategoryResponse;
import com.aptech.group.model.CategoryEntity;
import com.aptech.group.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.aptech.group.controller.CategoryServiceEndpoints.CATEGORY_SERVICE_PATH;

@RequestMapping(CATEGORY_SERVICE_PATH)
@RestController
@Slf4j
@RequiredArgsConstructor
public class CategoryServiceController {
    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getAll() {
        return categoryService.getAll();
    }

    @PostMapping
    public ResponseEntity<CategoryEntity> createCategory(@RequestBody CategoryRequest categoryRequest) {
        log.info("new product created {}", categoryRequest);
        return categoryService.createCategory(categoryRequest);
    }

    @PutMapping(path = "{categorySlug}")
    public ResponseEntity<CategoryEntity> updateCategory(
            @PathVariable("categorySlug") String slug,
            @RequestBody CategoryRequest categoryRequest) {
        return categoryService.updateCategory(categoryRequest, slug);
    }

    @DeleteMapping(path="{categoryId}")
    public ResponseEntity deleteCategory(@PathVariable("categoryId") String id) {
        return categoryService.deleteCategory(id);
    }
}
