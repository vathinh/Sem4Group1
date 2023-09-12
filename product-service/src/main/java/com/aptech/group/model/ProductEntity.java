package com.aptech.group.model;

import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product")
@Setter
@Getter
public class ProductEntity {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @NotNull
    private String name;

    @NotNull
    private String slug;

    @NotNull
    private BigDecimal price;

    private String condition;

    @NotNull
    private String description;

    @NotNull
    private Integer quantity;

    private String thumbnail;

    @ElementCollection(targetClass=String.class)
    private List<String> images;

    @OneToOne
    @JoinColumn(name = "brand")
    private BrandEntity brand;

    @OneToOne
    @JoinColumn(name = "category")
    private CategoryEntity category;

    @CreatedBy
    private String createdBy;

    @CreatedDate
    private Date createdDate;

    @LastModifiedDate
    private String lastModifiedDate;

    @LastModifiedBy
    private Date lastModifiedBy;
}
