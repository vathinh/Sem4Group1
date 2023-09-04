package com.aptech.group.service;

import com.aptech.group.dto.product.ProductRequest;
import com.aptech.group.dto.product.ProductResponse;
import com.aptech.group.model.ProductEntity;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProductService {
    ResponseEntity<ProductEntity> createProduct(ProductRequest request);
    ResponseEntity<List<ProductResponse>> getAll();
    ResponseEntity deleteProduct(String productId);
    ResponseEntity<ProductEntity> updateProduct(ProductRequest request, String productSlug);
}
