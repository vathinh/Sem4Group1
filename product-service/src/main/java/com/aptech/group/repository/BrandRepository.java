package com.aptech.group.repository;

import com.aptech.group.model.BrandEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BrandRepository extends JpaRepository<BrandEntity, String> {
    Optional<BrandEntity> findBySlug(String slug);
}
