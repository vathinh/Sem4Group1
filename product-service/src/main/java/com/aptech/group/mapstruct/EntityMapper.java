package com.aptech.group.mapstruct;

import com.aptech.group.repository.BrandRepository;
import com.aptech.group.repository.CategoryRepository;
import com.aptech.group.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class EntityMapper {
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    BrandRepository brandRepository;

    @Autowired
    CategoryRepository categoryRepository;
}
