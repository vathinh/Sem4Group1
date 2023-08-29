package com.aptech.group.config;

import com.aptech.group.exception.ExceptionHandlingAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
public class ExceptionConfig {
    @Bean
    public ExceptionHandlingAspect exceptionHandlingAspect() {
        return new ExceptionHandlingAspect();
    }
}
