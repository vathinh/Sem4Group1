package com.aptech.group.dto.title;


import com.aptech.group.model.TitleEntity;
import com.aptech.group.validator.NoExistRecord;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TitleRequest {
  @NotBlank
  @NoExistRecord(message = "Name is existed", fieldName = "name", entityName = TitleEntity.class)
  private String name;
  @NotBlank
  @NoExistRecord(message = "Code is existed", fieldName = "code", entityName = TitleEntity.class)
  private String code;
  private Long version;
}
