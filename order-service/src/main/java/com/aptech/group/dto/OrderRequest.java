package com.aptech.group.dto;

import com.aptech.group.model.OrderProductEntity;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {
    private String status;
    private List<OrderProductEntity> products;
}
