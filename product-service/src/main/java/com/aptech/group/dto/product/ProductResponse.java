package com.aptech.group.dto.product;

import com.aptech.group.model.BrandEntity;
import com.aptech.group.model.CategoryEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {
    private String id;
    private String name;
    private String slug;
    private BigDecimal price;
    private String description;
    private Integer quantity;
    private String thumbnail;
    private List<String> images;
    private BrandEntity brand;
    private CategoryEntity category;
    private String createdBy;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    private Date createdDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    private String lastModifiedDate;
    private Date lastModifiedBy;
}
