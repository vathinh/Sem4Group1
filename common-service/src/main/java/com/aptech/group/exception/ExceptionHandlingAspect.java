package com.aptech.group.exception;

import org.aspectj.lang.annotation.AfterThrowing;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ExceptionHandlingAspect {
    @AfterThrowing(pointcut = "execution(* com.aptech.*.*.*(..))", throwing = "ex")
    public ResponseEntity<ErrorMessage> handleException(Exception ex) {
        ErrorMessage errorMessage = new ErrorMessage("9999", ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @AfterThrowing(pointcut = "execution(* com.aptech.*.*.*(..)) && @annotation(commonException)", throwing = "ex")
    public ResponseEntity<ErrorMessage> handleCommonException(CommonException ex, CommonExceptionAnnotation commonException) {
        ErrorMessage errorMessage = new ErrorMessage(ex.getCode(), ex.getMessage(), ex.getStatus());
        return new ResponseEntity<>(errorMessage, ex.getStatus());
    }

    @AfterThrowing(pointcut = "execution(* com.aptech.*.*.*(..)) && @annotation(validateException)", throwing = "ex")
    public ResponseEntity<Map<String, String>> handleValidateException(ValidateException ex, ValidateExceptionAnnotation validateException) {
        Map<String, String> errorMap = new HashMap<>(ex.getMessageMap());
        return new ResponseEntity<>(errorMap, ex.getStatus());
    }
}
