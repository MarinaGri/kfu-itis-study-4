package ru.itis.framework.invokers;

import org.springframework.stereotype.Component;
import ru.itis.framework.converters.EntityToStringConverter;
import ru.itis.framework.converters.RequestToEntityConverter;
import ru.itis.framework.messages.Request;

import java.lang.reflect.Method;

@Component
public class RequestEntityMethodInvoker extends AbstractParamsMethodInvoker {

    private final RequestToEntityConverter converter;

    public RequestEntityMethodInvoker(EntityToStringConverter toStrConverter, RequestToEntityConverter converter) {
        super(toStrConverter);
        this.converter = converter;
    }

    @Override
    protected boolean isRelevantType(Class<?> parameterType) {
        return !parameterType.equals(Request.class);
    }

    @Override
    protected Object getValue(Method method, Request request) {
        converter.setClazz(getCurrentParamType());
        return converter.convert(request);
    }

}
