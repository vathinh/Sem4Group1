package com.aptech.group.repository;

import com.aptech.group.model.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<CategoryEntity, String> {
    Optional<CategoryEntity> findBySlug(String slug);
    List<CategoryEntity> findByParent(String parent);
}
