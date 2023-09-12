package com.aptech.group.controller;

import com.aptech.group.dto.product.ProductRequest;
import com.aptech.group.dto.product.ProductResponse;
import com.aptech.group.model.ProductEntity;
import com.aptech.group.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.aptech.group.controller.ProductServiceEndpoints.PRODUCT_SERVICE_PATH;

@RequestMapping(PRODUCT_SERVICE_PATH)
@RestController
@Slf4j
@RequiredArgsConstructor
public class ProductServiceController {
    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAll() {
            return productService.getAll();
    }

    @PostMapping
    public ResponseEntity<ProductEntity> createProduct(@RequestBody ProductRequest productRequest) {
        log.info("new product created {}", productRequest);
        return productService.createProduct(productRequest);
    }

    @PutMapping(path = "{productSlug}")
    public ResponseEntity<ProductEntity> updateProduct(
            @PathVariable("productSlug") String slug,
            @RequestBody ProductRequest productRequest) {
        return productService.updateProduct(productRequest, slug);
    }

    @DeleteMapping(path="{productId}")
    public ResponseEntity deleteProduct(@PathVariable("productId") String id) {
        return productService.deleteProduct(id);
    }
}
