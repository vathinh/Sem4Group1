package com.aptech.group.service.impl;

import com.aptech.group.dto.product.ProductRequest;
import com.aptech.group.dto.product.ProductResponse;
import com.aptech.group.mapstruct.ProductMapper;
import com.aptech.group.model.ProductEntity;
import com.aptech.group.repository.ProductRepository;
import com.aptech.group.service.ProductService;
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
public class ProductServiceImpl implements ProductService {
    @Autowired
    private final ProductRepository productRepository;

    private final ProductMapper mapper;

    @Override
    public ResponseEntity<ProductEntity> createProduct(ProductRequest request) {
        ProductEntity productEntity = mapper.toEntity(request);
        return new ResponseEntity<>(productRepository.save(productEntity), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<ProductResponse>> getAll() {
        return ResponseEntity.ok(productRepository.findAll().stream().map(mapper::toResponse).toList());
    }

    @Override
    public ResponseEntity deleteProduct(String productId) {
        productRepository.deleteById(productId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ProductEntity> updateProduct(ProductRequest request, String productSlug) {
        Optional<ProductEntity> productData = productRepository.findBySlug(productSlug);
        return new ResponseEntity<>(productRepository.save(productData.get()), HttpStatus.OK);
    }
}
