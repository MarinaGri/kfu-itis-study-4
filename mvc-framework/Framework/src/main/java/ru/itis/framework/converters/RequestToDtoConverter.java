package ru.itis.framework.converters;

import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;
import ru.itis.framework.exceptions.ConversionFailException;
import ru.itis.framework.messages.Request;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

/**
 * Сan only convert entities with default constructor and fields that have type String or int|long|boolean|double
 * (or their wrapper classes). If field has a different type, null will be placed
 */

@Component
public class RequestToDtoConverter implements RequestToEntityConverter {

    private Class<?> clazz;

    @Override
    public Object convert(Request request) {

        if(clazz == null){
            throw new IllegalStateException("Clazz has not been initialized yet");
        }

        try {
            Constructor<?> constructor = ReflectionUtils.accessibleConstructor(clazz);
            Object dto = constructor.newInstance();
            Field[] fields = clazz.getDeclaredFields();

            for (Field field : fields) {
                String[] params = request.getParameters().get(field.getName());

                if (params != null && params.length == 1 && params[0] != null) {
                    String str = params[0];

                    field.setAccessible(true);

                    Object value;
                    try {
                        value = convert(str, field.getType());
                    } catch (NumberFormatException | NullPointerException ex){
                        value = null;
                    }

                    if(value != null) {
                        ReflectionUtils.setField(field, dto, value);
                    }
                }
            }

            return dto;

        } catch (InvocationTargetException | NoSuchMethodException |
                InstantiationException | IllegalAccessException ex) {
           throw new ConversionFailException("Cant convert to " + clazz.getName(), ex);
        }

    }

    private Object convert(String value, Class<?> clazz) {
        if (clazz.equals(String.class)) {
            return value;
        }

        if (clazz.equals(Integer.class) || clazz.equals(int.class)) {
            return Integer.parseInt(value);
        }

        if (clazz.equals(Long.class) || clazz.equals(long.class)) {
            return Long.parseLong(value);
        }

        if (clazz.equals(Boolean.class) || clazz.equals(boolean.class)) {
            return Boolean.parseBoolean(value);
        }

        if (clazz.equals(Double.class) || clazz.equals(double.class)) {
            return Double.parseDouble(value);
        }

        return null;
    }

    @Override
    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }
}
