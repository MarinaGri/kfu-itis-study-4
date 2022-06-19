package ru.itis.framework.invokers;

import ru.itis.framework.messages.Request;
import ru.itis.framework.messages.Response;
import ru.itis.framework.routing.HandlerInfo;


import java.lang.reflect.Method;

public interface MethodInvoker {

    boolean canHandle(Method method);

    Response invoke(HandlerInfo route, Request request);

}
