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

import java.text.Normalizer;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@Slf4j
public class BrandServiceImpl implements BrandService {

    @Autowired
    private final BrandRepository brandRepository;

    private static final Pattern NONLATIN = Pattern.compile("[^\\w-]");
    private static final Pattern WHITESPACE = Pattern.compile("[\\s]");

    public static String toSlug(String input) {
        String nowhitespace = WHITESPACE.matcher(input).replaceAll("-");
        String normalized = Normalizer.normalize(nowhitespace, Normalizer.Form.NFD);
        String slug = NONLATIN.matcher(normalized).replaceAll("");
        return slug.toLowerCase(Locale.ENGLISH);
    }
    private final BrandMapper mapper;

    @Override
    public ResponseEntity<BrandEntity> createBrand(BrandRequest brandRequest) {
        BrandEntity brandData = BrandEntity.builder()
                .name(brandRequest.getName())
                .slug(toSlug(brandRequest.getName()))
                .thumbnail(brandRequest.getThumbnail())
                .build();

        return new ResponseEntity<BrandEntity>(
                brandRepository.save(brandData),
                HttpStatus.OK) ;
    }

    @Override
    public ResponseEntity<List<BrandResponse>> getAll() {
        return ResponseEntity.ok(brandRepository.findAll().stream().map(mapper::toResponse).toList());
    }

    @Override
    public ResponseEntity<BrandEntity> updateBrand(String brandId, BrandRequest request) {
        Optional<BrandEntity> brandData = brandRepository.findById(brandId);
        if (brandData.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if(request.getName() != null)
            brandData.get().setName(request.getName());
        brandData.get().setSlug(toSlug(request.getName()));
        if(request.getThumbnail() != null)
            brandData.get().setThumbnail(request.getThumbnail());
        return new ResponseEntity<>(brandRepository.save(brandData.get()), HttpStatus.OK);
    }

    @Override
    public ResponseEntity deleteBrand(String brandId) {
        Optional<BrandEntity> brandData = brandRepository.findById(brandId);
        if (brandData.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        brandRepository.deleteById(brandId);
        return new ResponseEntity(HttpStatus.OK);
    }
}
