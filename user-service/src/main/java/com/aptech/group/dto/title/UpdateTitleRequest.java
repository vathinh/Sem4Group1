package com.aptech.group.dto.title;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateTitleRequest {
  @NotBlank
  private String code;
  @NotBlank
  private String name;
  private Long version;
}
