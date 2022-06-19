package ru.itis.validation.annotations;

import ru.itis.validation.validators.CollectionSizeValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CollectionSizeValidator.class)
@Documented
public @interface CollectionSizeChecker {

    String[] fieldsNames() default {};

    String message() default "";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
