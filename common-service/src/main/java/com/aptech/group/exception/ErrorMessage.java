package com.aptech.group.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@Getter
@Setter
public class ErrorMessage {
    private String code;
    private String message;
    private HttpStatus status;
}
