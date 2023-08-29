package com.aptech.group.exception;

import lombok.*;
import org.springframework.http.HttpStatus;

@EqualsAndHashCode(callSuper = true)
@Data
@Getter
@Setter
@AllArgsConstructor
@Builder
public class CantDeleteException extends RuntimeException{
    private final String code;
    private final String message;
    private final HttpStatus status;

}
