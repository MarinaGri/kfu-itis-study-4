package ru.itis.framework.invokers;

import ru.itis.framework.converters.EntityToStringConverter;
import ru.itis.framework.messages.Request;

import java.lang.reflect.Method;

public abstract class AbstractParamsMethodInvoker extends AbstractMethodInvoker {

    private Class<?> currentParamType;

    public AbstractParamsMethodInvoker(EntityToStringConverter converter) {
        super(converter);
    }

    protected abstract boolean isRelevantType(Class<?> parameterType);

    protected abstract Object getValue(Method method, Request request);

    @Override
    protected boolean isRelevantParameters(Class<?>[] parameterTypes) {
        if (isOneParam(parameterTypes) && isRelevantType(parameterTypes[0])) {
            return true;
        }

        if (parameterTypes.length == 2 && parameterTypes[0] != null && parameterTypes[1] != null) {
            return isFirstRequest(parameterTypes) || isSecondRequest(parameterTypes);
        }

        return false;
    }

    @Override
    protected Object[] getParams(Method method, Request request) {
        Class<?>[] classes = method.getParameterTypes();
        Object[] params;

        if (isOneParam(classes)) {
            currentParamType = classes[0];

            params = new Object[1];
            params[0] = getValue(method, request);

        } else {

            int reqInd = isFirstRequest(classes) ? 0 : 1;
            int entInd = Math.abs(reqInd - 1);

            params = new Object[2];
            currentParamType = classes[entInd];

            params[reqInd] = request;
            params[entInd] = getValue(method, request);

        }
        return params;
    }

    private boolean isFirstRequest(Class<?>[] parameterTypes) {
        return parameterTypes[0].equals(Request.class) && isRelevantType(parameterTypes[1]);
    }

    private boolean isSecondRequest(Class<?>[] parameterTypes) {
        return isRelevantType(parameterTypes[0]) && parameterTypes[1].equals(Request.class);
    }

    private boolean isOneParam(Class<?>[] parameterTypes) {
        return parameterTypes.length == 1 && parameterTypes[0] != null;
    }

    public Class<?> getCurrentParamType() {
        return currentParamType;
    }
}
