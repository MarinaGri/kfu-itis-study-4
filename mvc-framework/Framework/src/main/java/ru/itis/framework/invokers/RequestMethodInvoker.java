package ru.itis.framework.invokers;

import org.springframework.stereotype.Component;
import ru.itis.framework.converters.EntityToStringConverter;
import ru.itis.framework.messages.Request;

import java.lang.reflect.Method;

@Component
public class RequestMethodInvoker extends AbstractMethodInvoker {

    public RequestMethodInvoker(EntityToStringConverter converter) {
        super(converter);
    }

    @Override
    protected Object[] getParams(Method method, Request request) {
        return new Object[]{request};
    }
}
