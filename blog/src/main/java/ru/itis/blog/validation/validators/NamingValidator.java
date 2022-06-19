package ru.itis.blog.validation.validators;

import ru.itis.blog.validation.annotations.Naming;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class NamingValidator implements ConstraintValidator<Naming, String> {

    private final String NAME_REGEX = "(?:(?:\\w|[а-яА-ЯA-Z])+\\s?(?:\\w|[а-яА-ЯA-Z])*\\s?)+";

    @Override
    public boolean isValid(String name, ConstraintValidatorContext context) {
        if (!Pattern.compile(NAME_REGEX).matcher(name).matches()) {
            context.buildConstraintViolationWithTemplate("{title.Naming}").addConstraintViolation();
            return false;
        }
        return true;
    }
}
