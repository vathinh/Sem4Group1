package com.aptech.group.dto.title;

import com.aptech.group.dto.PagingRequest;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import java.time.ZonedDateTime;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TitleCriteria implements PagingRequest {
  private Integer code;
  private String name;
  private String search;
  private int size;
  private int page;
  private String lastedValue = "";
  private int lastedPage;
  private String firstValue = "";
 private List<String> sort;

  @DateTimeFormat(iso = ISO.DATE_TIME)
  private ZonedDateTime from;

  @DateTimeFormat(iso = ISO.DATE_TIME)
  private ZonedDateTime to;
}
