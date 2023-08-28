package com.aptech.group.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = NotExistValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface NoExistRecord {
    String message() default "Value is existed";
    String fieldName() default "id";
    Class<?> entityName();
    boolean isDeleteFlag() default true;

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
