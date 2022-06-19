package ru.itis.framework.invokers;


import org.springframework.util.ReflectionUtils;
import ru.itis.framework.converters.EntityToStringConverter;
import ru.itis.framework.messages.Request;
import ru.itis.framework.messages.Response;
import ru.itis.framework.routing.HandlerInfo;

import java.lang.reflect.Method;

public abstract class AbstractMethodInvoker implements MethodInvoker {

    private final EntityToStringConverter converter;

    public AbstractMethodInvoker(EntityToStringConverter converter) {
        this.converter = converter;
    }

    protected abstract Object[] getParams(Method method, Request request);

    @Override
    public boolean canHandle(Method method) {
        return isRelevantReturnType(method.getReturnType()) &&
                isRelevantParameters(method.getParameterTypes());
    }

    @Override
    public Response invoke(HandlerInfo route, Request request) {
        Method method = route.getMethod();
        Class<?> returnType = method.getReturnType();
        Object[] params = getParams(method, request);
        Object response = ReflectionUtils.invokeMethod(method, route.getHandler(), params);

        if (returnType.equals(Response.class)) {
            return (Response) response;
        }

        if (returnType.equals(String.class)){
            return Response.builder().viewName((String) response).build();
        }

        return Response.builder().body(converter.convert(response)).build();
    }

    protected boolean isRelevantReturnType(Class<?> returnType) {
        return !returnType.equals(void.class);
    }

    protected boolean isRelevantParameters(Class<?>[] parameterTypes) {
        return parameterTypes.length == 1 && parameterTypes[0] != null
                && parameterTypes[0].equals(Request.class);
    }

}
