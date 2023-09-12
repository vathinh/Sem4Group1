package com.aptech.group.mapstruct;

import com.aptech.group.dto.category.CategoryRequest;
import com.aptech.group.dto.category.CategoryResponse;
import com.aptech.group.dto.product.ProductRequest;
import com.aptech.group.dto.product.ProductResponse;
import com.aptech.group.model.CategoryEntity;
import com.aptech.group.model.ProductEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = EntityMapper.class)

public interface ProductMapper {
    ProductResponse toResponse(ProductEntity productEntity);
    ProductEntity toEntity(ProductRequest productRequest);
}
