package com.aptech.group.repository;

import com.aptech.group.model.ProductEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<ProductEntity, String> {
    Optional<ProductEntity> findBySlug(String slug);

    List<ProductEntity> findByIdInOrderByCreatedDateDesc(List<String> idList, Pageable pageable);

    List<ProductEntity> findBySlugContainingOrderByCreatedDateDesc(String slug, Pageable pageable);

    List<ProductEntity> findByCategory_SlugInOrderByCreatedDateDesc(List<String> categories, Pageable pageable);

    List<ProductEntity> findBySlugContainingAndCategory_SlugInOrderByCreatedDateDesc(String slug, List<String> categories, Pageable pageable);
}
