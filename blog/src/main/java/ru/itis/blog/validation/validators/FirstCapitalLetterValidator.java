package ru.itis.blog.validation.validators;

import ru.itis.blog.validation.annotations.FirstCapitalLetter;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class FirstCapitalLetterValidator implements ConstraintValidator<FirstCapitalLetter, String> {

    private final String REGEX = "[A-ZА-Я].*";

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (!Pattern.compile(REGEX).matcher(value).matches()) {
            context.buildConstraintViolationWithTemplate("{FirstCapitalLetter}").addConstraintViolation();
            return false;
        }
        return true;
    }
}
