package com.aptech.group.mapstruct;

import com.aptech.group.dto.category.CategoryRequest;
import com.aptech.group.dto.category.CategoryResponse;
import com.aptech.group.model.CategoryEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = EntityMapper.class)
public interface CategoryMapper {
    CategoryResponse toResponse(CategoryEntity categoryEntity);
    CategoryEntity toEntity(CategoryRequest categoryRequest);
}
