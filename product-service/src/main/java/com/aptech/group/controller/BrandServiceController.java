package com.aptech.group.controller;

import com.aptech.group.dto.brand.BrandRequest;
import com.aptech.group.dto.brand.BrandResponse;
import com.aptech.group.model.BrandEntity;
import com.aptech.group.service.BrandService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.aptech.group.controller.BrandServiceEndpoints.BRAND_SERVICE_PATH;

@RequestMapping(BRAND_SERVICE_PATH)
@RestController
@Slf4j
@RequiredArgsConstructor
public class BrandServiceController {
    private final BrandService brandService;

    @GetMapping
    public ResponseEntity<List<BrandResponse>> getAll() {
        return brandService.getAll();
    }

    @PostMapping
    public ResponseEntity<BrandEntity> createBrand(@RequestBody BrandRequest brandRequest) {
        log.info("new product created {}", brandRequest);
        return brandService.createBrand(brandRequest);
    }

    @PutMapping(path = "{brandSlug}")
    public ResponseEntity<BrandEntity> updateBrand(
            @PathVariable("brandSlug") String slug,
            @RequestBody BrandRequest brandRequest) {
        return brandService.updateBrand(brandRequest, slug);
    }

    @DeleteMapping(path="{brandId}")
    public ResponseEntity deleteBrand(@PathVariable("brandId") String id) {
        return brandService.deleteBrand(id);
    }
}
