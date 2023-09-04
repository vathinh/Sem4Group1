package com.aptech.group.dto.brand;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BrandResponse {
    private String id;
    private String name;
    private String slug;
    private String thumbnail;
}
