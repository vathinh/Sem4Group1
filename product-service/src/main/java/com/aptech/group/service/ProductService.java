package com.aptech.group.service;

import com.aptech.group.dto.product.ProductRequest;
import com.aptech.group.dto.product.ProductResponse;
import com.aptech.group.model.ProductEntity;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProductService {
    ResponseEntity<ProductEntity> createProduct(ProductRequest request, String userId);
    ResponseEntity<List<ProductResponse>> getAll(
            List<String> idList,
            String slug,
            String categorySlug,
            Integer page,
            Integer limit
    );
    ResponseEntity deleteProduct(String productId);
    ResponseEntity<ProductEntity> updateProduct(String productSlug, ProductRequest request, String userId);
}
