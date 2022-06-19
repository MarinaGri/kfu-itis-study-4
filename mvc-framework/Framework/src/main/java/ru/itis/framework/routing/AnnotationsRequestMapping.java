package ru.itis.framework.routing;


import org.springframework.stereotype.Component;
import ru.itis.framework.annotaions.Controller;
import ru.itis.framework.annotaions.RequestMapping;
import ru.itis.framework.messages.RequestDto;
import ru.itis.framework.utils.PathPatternMatcher;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static ru.itis.framework.messages.RequestDtoMapper.from;


@Component
public class AnnotationsRequestMapping extends AbstractRequestMapping {

    private final PathPatternMatcher matcher;

    public AnnotationsRequestMapping(PathPatternMatcher matcher) {
        this.matcher = matcher;
    }

    @Override
    protected HandlerInfo createRouteInfo(Object handler, Method method) {
        return new HandlerInfo(handler, method);
    }

    @Override
    protected boolean arePathsEqual(Method method, String methodPath, String path) {
        return matcher.isMatch(path, methodPath, getRequestMapping(method).placeholder());
    }

    @Override
    protected List<Class<?>> getHandlers() {
        List<Class<?>> controllers = new ArrayList<>();
        String[] beanNames = context.getBeanNamesForAnnotation(Controller.class);
        for (String beanName : beanNames) {
            controllers.add(context.getType(beanName));
        }
        return controllers;
    }

    @Override
    protected RequestDto getRequest(Method method) {
        return from(getRequestMapping(method));
    }

    @Override
    protected boolean isRelevantMethod(Method method) {
        RequestMapping[] annotations = method.getAnnotationsByType(RequestMapping.class);

        return annotations != null && annotations.length > 0 && annotations[0] != null;
    }

    private RequestMapping getRequestMapping(Method method){
        return method.getAnnotationsByType(RequestMapping.class)[0];
    }

}
