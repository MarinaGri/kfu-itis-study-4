package ru.itis.validation.validators;


import org.springframework.beans.BeanWrapperImpl;
import ru.itis.validation.annotations.CollectionSizeChecker;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Collection;
import java.util.Map;

public class CollectionSizeValidator implements ConstraintValidator<CollectionSizeChecker, Object> {

    private String errorMessage = "Field is not a collection";

    private String[] fieldsNames;

    @Override
    public void initialize(final CollectionSizeChecker checker) {
        fieldsNames = checker.fieldsNames();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        final BeanWrapperImpl bean = new BeanWrapperImpl(value);
        for (String fieldName : fieldsNames) {
            final Object obj = bean.getPropertyValue(fieldName);

            if (obj instanceof Collection) {
                Collection<?> collection = (Collection<?>) obj;
                if (collection.size() < 1) {
                    return false;
                }
            }

            if (obj instanceof Map) {
                Map<?, ?> map = (Map<?, ?>) obj;
                if (map.isEmpty()) {
                    return false;
                }
            }

            if (!(obj instanceof Map) && (!(obj instanceof Collection))) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(errorMessage)
                        .addPropertyNode(fieldName)
                        .addConstraintViolation();
                return false;
            }
        }
        return true;
    }
}
