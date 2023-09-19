package com.aptech.group.dto.title;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TitleRequest {
  @NotBlank
  private String name;
  @NotBlank
  private String code;
  private Long version;
}
