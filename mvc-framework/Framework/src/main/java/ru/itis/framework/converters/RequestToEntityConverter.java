package ru.itis.framework.converters;

import ru.itis.framework.messages.Request;


public interface RequestToEntityConverter extends Converter<Request, Object> {

    void setClazz(Class<?> clazz);

}
