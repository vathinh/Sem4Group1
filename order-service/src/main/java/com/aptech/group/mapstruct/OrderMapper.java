package com.aptech.group.mapstruct;

import com.aptech.group.dto.OrderRequest;
import com.aptech.group.dto.OrderResponse;
import com.aptech.group.model.OrderEntity;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring", uses = EntityMapper.class)
public interface OrderMapper {
    OrderResponse toResponse(OrderEntity orderEntity);

    OrderEntity toEntity(OrderRequest orderRequest);
}
