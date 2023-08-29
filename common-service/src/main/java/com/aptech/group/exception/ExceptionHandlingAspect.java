package com.aptech.group.exception;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
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
    @AfterThrowing(pointcut = "execution(* com.aptech.*.*.*(..)) && @annotation(duplicatedException)", throwing = "ex")
    public ResponseEntity<?> handleDuplicatedException(DuplicatedException ex) {
        ErrorMessage errorMessage = new ErrorMessage(ex.getCode(), ex.getMessage(), ex.getStatus());
        return new ResponseEntity<>(errorMessage, ex.getStatus());
    }

    @AfterThrowing(pointcut = "execution(* com.aptech.*.*.*(..)) && @annotation(cantDeleteException)", throwing = "ex")
    public ResponseEntity<?> handleCantDeleteException(CantDeleteException ex) {
        ErrorMessage errorMessage = new ErrorMessage(ex.getCode(), ex.getMessage(), ex.getStatus());
        return new ResponseEntity<>(errorMessage, ex.getStatus());
    }
    @AfterThrowing(pointcut = "execution(* com.aptech.*.*.*(..)) && @annotation(notFoundException)", throwing = "ex")
    public ResponseEntity<?> handleNotFoundException(NotFoundException ex, NotFoundExceptionAnnotation notFoundException) {
        ErrorMessage errorMessage = new ErrorMessage(ex.getCode(), ex.getMessage(), ex.getStatus());
        return new ResponseEntity<>(errorMessage, ex.getStatus());
    }


}
