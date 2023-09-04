package com.aptech.group.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "payment")
public class PaymentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(name="name")
    private String name;

    @Column(name="type")
    private String type;

    @Column(name="balance")
    private BigDecimal balance;

    @Column(name="userId")
    private String userId;
}
