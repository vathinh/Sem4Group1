package com.aptech.group.service.impl;

import com.aptech.group.dto.brand.BrandRequest;
import com.aptech.group.dto.brand.BrandResponse;
import com.aptech.group.dto.category.CategoryRequest;
import com.aptech.group.dto.category.CategoryResponse;
import com.aptech.group.mapstruct.BrandMapper;
import com.aptech.group.mapstruct.CategoryMapper;
import com.aptech.group.model.BrandEntity;
import com.aptech.group.model.CategoryEntity;
import com.aptech.group.repository.BrandRepository;
import com.aptech.group.repository.CategoryRepository;
import com.aptech.group.service.BrandService;
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
public class BrandServiceImpl implements BrandService {

    @Autowired
    private final BrandRepository brandRepository;

    private final BrandMapper mapper;

    @Override
    public ResponseEntity<BrandEntity> createBrand(BrandRequest brandRequest) {
        BrandEntity brandEntity = mapper.toEntity(brandRequest);
        return new ResponseEntity<>(brandRepository.save(brandEntity), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<BrandResponse>> getAll() {
        return ResponseEntity.ok(brandRepository.findAll().stream().map(mapper::toResponse).toList());
    }

    @Override
    public ResponseEntity<BrandEntity> updateBrand(BrandRequest brandRequest, String brandSlug) {
        Optional<BrandEntity> brandData = brandRepository.findBySlug(brandSlug);
        return new ResponseEntity<>(brandRepository.save(brandData.get()), HttpStatus.OK);
    }

    @Override
    public ResponseEntity deleteBrand(String brandId) {
        brandRepository.deleteById(brandId);
        return new ResponseEntity(HttpStatus.OK);
    }
}
