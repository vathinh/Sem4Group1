package com.aptech.group.mapstruct;

import com.aptech.group.dto.brand.BrandRequest;
import com.aptech.group.dto.brand.BrandResponse;
import com.aptech.group.model.BrandEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = EntityMapper.class)
public interface BrandMapper {
    BrandResponse toResponse(BrandEntity brandEntity);
    BrandEntity toEntity(BrandRequest brandRequest);
}
