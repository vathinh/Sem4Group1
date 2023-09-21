package com.aptech.group.service.impl;

import com.aptech.group.dto.product.ProductRequest;
import com.aptech.group.dto.product.ProductResponse;
import com.aptech.group.mapstruct.ProductMapper;
import com.aptech.group.model.BrandEntity;
import com.aptech.group.model.CategoryEntity;
import com.aptech.group.model.ProductEntity;
import com.aptech.group.repository.BrandRepository;
import com.aptech.group.repository.CategoryRepository;
import com.aptech.group.repository.ProductRepository;
import com.aptech.group.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {
    @Autowired
    private final ProductRepository productRepository;

    @Autowired
    private final CategoryRepository categoryRepository;

    @Autowired
    private final BrandRepository brandRepository;

    private final ProductMapper mapper;

    private static final Pattern NONLATIN = Pattern.compile("[^\\w-]");
    private static final Pattern WHITESPACE = Pattern.compile("[\\s]");

    public static String toSlug(String input) {
        String nowhitespace = WHITESPACE.matcher(input).replaceAll("-");
        String normalized = Normalizer.normalize(nowhitespace, Normalizer.Form.NFD);
        String slug = NONLATIN.matcher(normalized).replaceAll("");
        return slug.toLowerCase(Locale.ENGLISH);
    }

    @Override
    public ResponseEntity<ProductEntity> createProduct(ProductRequest request, String userId) {
        Optional<BrandEntity> brandData = brandRepository.findById(request.getBrand().getId());
        if(brandData.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<CategoryEntity> cateList = new ArrayList<>();
        Optional<CategoryEntity> categoryChild = categoryRepository.findById(request.getCategory().getId());
        if(categoryChild.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        cateList.add(categoryChild.get());

        if(!categoryChild.get().getParent().isEmpty()) {
            Optional<CategoryEntity> categoryParent = categoryRepository.findById(categoryChild.get().getParent());
            if (categoryParent.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            cateList.add(categoryParent.get());
        }

        ProductEntity product = ProductEntity.builder()
                .name(request.getName())
                .slug(toSlug(request.getName()))
                .price(request.getPrice())
                .condition(request.getCondition())
                .description(request.getDescription())
                .quantity(request.getQuantity())
                .brand(brandData.get())
                .category(categoryChild.get())
                .thumbnail(request.getThumbnail())
                .images(request.getImages())
                .createdBy(userId)
                .lastModifiedBy(userId)
                .build();

        return new ResponseEntity<>(productRepository.save(product), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<ProductResponse>> getAll(
            List<String> idList,
            String slug,
            String categorySlug,
            Integer page,
            Integer limit
    ) {
        List<String> categories = new ArrayList<>();
        if(categorySlug != null) {
            categories.add(categorySlug);
            Optional<CategoryEntity> categoryData = categoryRepository.findBySlug(categorySlug);
            if(categoryData != null && categoryData.get().getParent().isEmpty()) {
                List<CategoryEntity> categoryChild = categoryRepository.findByParent(categoryData.get().getId());
                List<String> cateSlugs = categoryChild.stream().map(CategoryEntity::getSlug).toList();
                categories = cateSlugs;
            }
        }

        Pageable pageableRequest = PageRequest.of(page, limit);
        if(slug != null) {
            if(!categories.isEmpty()) {
                return ResponseEntity.ok(productRepository.findBySlugContainingAndCategory_SlugInOrderByCreatedDateDesc(
                        slug, categories, pageableRequest).stream().map(mapper::toResponse).toList());
            }
            return ResponseEntity.ok(productRepository.findBySlugContainingOrderByCreatedDateDesc(slug, pageableRequest).stream().map(mapper::toResponse).toList());
        }

        if(idList != null) {
            return ResponseEntity.ok(productRepository.findByIdInOrderByCreatedDateDesc(idList, pageableRequest).stream().map(mapper::toResponse).toList());
        }

        return ResponseEntity.ok(productRepository.findAll().stream().map(mapper::toResponse).toList());
    }

    @Override
    public ResponseEntity deleteProduct(String productId) {
        Optional<ProductEntity> productData = productRepository.findById(productId);
        if (productData.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        productRepository.deleteById(productId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ProductEntity> updateProduct(String productSlug, ProductRequest request, String userId) {
        Optional<ProductEntity> productData = productRepository.findBySlug(productSlug);
        if(productData.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        //update
        if(request.getName() != null) {
            productData.get().setName(request.getName());
            productData.get().setSlug(toSlug(request.getName()));
        }
        if(request.getPrice() != null) productData.get().setPrice(request.getPrice());
        if(request.getCondition() != null) productData.get().setCondition(request.getCondition());
        if(request.getDescription() != null) productData.get().setDescription(request.getDescription());
        if(request.getQuantity() != null) productData.get().setQuantity(request.getQuantity());
        if(request.getBrand() != null) {
            //find brand data
            Optional<BrandEntity> brandData = brandRepository.findById(request.getBrand().getId());
            if (brandData.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            productData.get().setBrand(brandData.get());
        }
        if(request.getCategory() != null) {
            //find category data
            List<CategoryEntity> cateList = new ArrayList<>();
            Optional<CategoryEntity> categoryChild = categoryRepository.findById(request.getCategory().getId());
            if (categoryChild.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            cateList.add(categoryChild.get());

            if(!categoryChild.get().getParent().isEmpty()) {
                Optional<CategoryEntity> categoryParent = categoryRepository.findById(categoryChild.get().getParent());
                if (categoryParent.isEmpty()) {
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
                cateList.add(categoryParent.get());
            }

            productData.get().setCategory(categoryChild.get());
        }
        if(request.getThumbnail() != null) productData.get().setThumbnail(request.getThumbnail());
        if(request.getImages() != null) productData.get().setImages(request.getImages());
        productData.get().setLastModifiedBy(userId);
        return new ResponseEntity<>(productRepository.save(productData.get()), HttpStatus.OK);
    }
}
