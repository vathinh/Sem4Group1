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
    public ResponseEntity<List<ProductResponse>> getAll(
            @RequestParam(name = "id", required = false) List<String> idList,
            @RequestParam(name = "s", required = false) String slug,
            @RequestParam(name = "c", required = false) String category,
            @RequestParam(name = "p", required = false, defaultValue = "0") String page,
            @RequestParam(name = "l", required = false, defaultValue = "10") String limit

    ) {
            return productService.getAll(
                    idList, slug, category,
                    Integer.parseInt(page), Integer.parseInt(limit));
    }

    @PostMapping
    public ResponseEntity<ProductEntity> createProduct(
            @RequestBody ProductRequest productRequest,
            @RequestAttribute String userId
    ) {
        log.info("new product created {}", productRequest);
        return productService.createProduct(productRequest, userId);
    }

    @PutMapping(path = "{productSlug}")
    public ResponseEntity<ProductEntity> updateProduct(
            @PathVariable("productSlug") String slug,
            @RequestBody ProductRequest productRequest,
            @RequestAttribute String userId) {
        return productService.updateProduct(slug, productRequest, userId);
    }

    @DeleteMapping(path="{productId}")
    public ResponseEntity deleteProduct(@PathVariable("productId") String id) {
        return productService.deleteProduct(id);
    }
}
