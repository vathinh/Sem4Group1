package com.aptech.group.dto.title;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.ZonedDateTime;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TitleResponse {
  private Integer id;
  private String name;
  private String code;
  private String createdBy;
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
  private ZonedDateTime createdDate;
  private Long version;
}
