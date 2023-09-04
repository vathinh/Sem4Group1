package com.aptech.group.dto;

import lombok.*;

import java.math.BigDecimal;

@Data
@Getter
@Setter
@Builder
public class PaymentResponse {
    private String name;
    private String type;
    private BigDecimal balance;
}
