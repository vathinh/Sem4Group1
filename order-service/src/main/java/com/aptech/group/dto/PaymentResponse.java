package com.aptech.group.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponse {
    private String id;
    private String type;
    private String name;
    private BigDecimal balance;
}
