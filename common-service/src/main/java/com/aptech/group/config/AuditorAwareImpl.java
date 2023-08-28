package com.aptech.group.config;

import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {

  @Override
  public Optional<String> getCurrentAuditor() {
    String author = "admin";
    return Optional.of(author);
  }
}
