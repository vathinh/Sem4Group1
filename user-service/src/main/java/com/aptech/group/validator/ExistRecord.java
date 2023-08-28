package com.aptech.group.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ExistValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ExistRecord {
    String message() default "Value is not existed";

    String fieldName() default "id";

    boolean ignoreNull() default false;

    boolean isDeleteFlag() default true;
    Class<?> entityName();

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
