package com.aptech.group.mapstruct;

import com.aptech.group.dto.PaymentRequest;
import com.aptech.group.dto.PaymentResponse;
import com.aptech.group.model.PaymentEntity;
import org.mapstruct.Mapper;

@Mapper
public interface PaymentMapper {
    PaymentEntity toEntity(PaymentRequest request);
    PaymentResponse toResponse(PaymentEntity entity);
}
