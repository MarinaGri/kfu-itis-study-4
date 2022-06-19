package ru.itis.framework.routing;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.lang.reflect.Method;


@Data
@AllArgsConstructor
public class HandlerInfo {
    private Object handler;
    private Method method;
}
