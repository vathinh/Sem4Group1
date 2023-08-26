package com.aptech.group.exception;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@Builder
@AllArgsConstructor
@Data
public class ValidateException extends RuntimeException{
    private final String code;
    private final Map<String, String> messageMap;
    private final HttpStatus status;

}
