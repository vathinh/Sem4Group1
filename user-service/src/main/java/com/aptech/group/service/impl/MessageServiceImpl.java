package com.aptech.group.service.impl;

import com.aptech.group.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

  private final MessageSource messageSource;
  @Override
  public String getMessage(String key) {
    Locale locale = LocaleContextHolder.getLocale();
    return messageSource.getMessage(key, null, locale);
  }

  @Override
  public String getMessage(String key, Object[] params) {
    try {
      Locale locale = LocaleContextHolder.getLocale();
      return messageSource.getMessage(key, params, locale);
    } catch (NoSuchMessageException ex) {
      return key;
    }
  }
}
