package ru.itis.cms.validation.validators;

import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;
import ru.itis.cms.validation.annotations.Password;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;


public class PasswordValidator implements ConstraintValidator<Password, String> {

    private final int MIN = 5;
    private final int MAX = 20;

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        boolean isValid = true;

        if (password.length() < MIN || password.length() > MAX) {
            isValid = false;
            HibernateConstraintValidatorContext hibernateContext = context.unwrap(
                    HibernateConstraintValidatorContext.class
            );
            hibernateContext
                    .addMessageParameter( "min", MIN )
                    .addMessageParameter( "max", MAX )
                    .buildConstraintViolationWithTemplate( "{validation.length}")
                    .addConstraintViolation();
        }

        if (!Pattern.compile("[0-9]").matcher(password).find()) {
            isValid = false;
            context.buildConstraintViolationWithTemplate("{validation.password.digit}").addConstraintViolation();
        }

        if (!Pattern.compile("[A-Z]").matcher(password).find()) {
            isValid = false;
            context.buildConstraintViolationWithTemplate("{validation.password.capital-letter}").addConstraintViolation();
        }

        if (!Pattern.compile("[a-z]").matcher(password).find()) {
            isValid = false;
            context.buildConstraintViolationWithTemplate("{validation.password.lowercase-letter}").addConstraintViolation();
        }

        return isValid;
    }
}
