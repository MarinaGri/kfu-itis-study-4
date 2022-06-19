package ru.itis.framework.annotaions;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface Controller {

    @AliasFor(annotation = Component.class)
    String value() default "";

}
