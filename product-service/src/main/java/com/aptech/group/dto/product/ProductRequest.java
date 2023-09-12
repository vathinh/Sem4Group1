package com.aptech.group.dto.product;

import com.aptech.group.model.BrandEntity;
import com.aptech.group.model.CategoryEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {
    @NotBlank
    private String name;

    @NotBlank
    private BigDecimal price;

    private String condition;

    @NotBlank
    private String description;

    @NotBlank
    private Integer quantity;

    private String thumbnail;

    private List<String> images;

    @NotBlank
    private BrandEntity brand;

    @NotBlank
    private CategoryEntity category;
}
