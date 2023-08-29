package com.aptech.group.controller;

import com.aptech.group.dto.title.TitleCriteria;
import com.aptech.group.dto.title.TitleRequest;
import com.aptech.group.dto.title.TitleResponse;
import com.aptech.group.dto.title.UpdateTitleRequest;
import com.aptech.group.exception.CommonExceptionAnnotation;
import com.aptech.group.model.TitleEntity;
import com.aptech.group.service.TitleService;
import com.aptech.group.validator.ExistRecord;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import javax.validation.Valid;
import java.util.Map;

import static com.aptech.group.controller.UserServiceEndpoints.TITLE_PATH;


@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping(TITLE_PATH)
@Validated
public class TitleController {
  private final TitleService titleService;

  @GetMapping
  @PreAuthorize("hasAnyRole(@environment.getProperty('title.read'), @environment.getProperty('title.full'))")
  public Page<TitleResponse> getAllTitles(@Valid TitleCriteria titleCriteria) {
    return titleService.getAllTitles(titleCriteria);
  }

  @GetMapping("{id}")
  @PreAuthorize("hasAnyRole(@environment.getProperty('title.read'), @environment.getProperty('title.full'))")
  public TitleResponse getTitleById(@PathVariable("id") @ExistRecord(entityName = TitleEntity.class) Integer id) {
    return titleService.getTitleById(id);
  }

  @PostMapping
  @ResponseStatus(code = HttpStatus.CREATED)
  @PreAuthorize("hasAnyRole(@environment.getProperty('title.create'), @environment.getProperty('title.full'))")
  public void createTitle(@RequestBody @Valid TitleRequest titleRequest) {
    titleService.createTitle(titleRequest);
  }

  @PutMapping("{id}")
  @PreAuthorize("hasAnyRole(@environment.getProperty('title.update'), @environment.getProperty('title.full'))")
  public void updateTitle(
      @PathVariable("id") @ExistRecord(entityName = TitleEntity.class) Integer id,
      @RequestBody @Valid UpdateTitleRequest updateTitleRequest) {
    titleService.updateTitle(id, updateTitleRequest);
  }

  @DeleteMapping
  @CommonExceptionAnnotation
  @PreAuthorize("hasAnyRole(@environment.getProperty('title.delete'), @environment.getProperty('title.full'))")
  public void deleteTitles(@RequestBody Map<Integer, Long> ids) throws HttpClientErrorException.BadRequest {
    titleService.deleteTitles(ids);
  }
}
