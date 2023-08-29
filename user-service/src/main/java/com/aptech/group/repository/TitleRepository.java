package com.aptech.group.repository;

import com.aptech.group.model.TitleEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TitleRepository extends JpaRepository<TitleEntity, Integer> {
    Page<TitleEntity> findAll(Specification<TitleEntity> specification, Pageable pageable);
}
