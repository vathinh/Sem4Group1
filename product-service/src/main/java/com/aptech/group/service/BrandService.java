package com.aptech.group.service;

import com.aptech.group.dto.brand.BrandRequest;
import com.aptech.group.dto.brand.BrandResponse;
import com.aptech.group.model.BrandEntity;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BrandService {
    ResponseEntity<BrandEntity> createBrand(BrandRequest brandRequest);
    ResponseEntity<List<BrandResponse>> getAll();
    ResponseEntity<BrandEntity> updateBrand(BrandRequest brandRequest, String brandSlug);
    ResponseEntity deleteBrand(String brandId);
}
