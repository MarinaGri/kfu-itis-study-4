package ru.itis.framework.invokers;

import org.springframework.stereotype.Component;
import ru.itis.framework.annotaions.RequestMapping;
import ru.itis.framework.converters.EntityToStringConverter;
import ru.itis.framework.messages.Request;
import ru.itis.framework.utils.PathPatternMatcher;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URLDecoder;


@Component
public class PathVariableMethodInvoker extends AbstractParamsMethodInvoker {

    private final PathPatternMatcher matcher;

    public PathVariableMethodInvoker(EntityToStringConverter converter, PathPatternMatcher matcher) {
        super(converter);
        this.matcher = matcher;
    }

    @Override
    protected boolean isRelevantType(Class<?> parameterType) {
        return parameterType.equals(String.class);
    }

    @Override
    protected Object getValue(Method method, Request request) {
        RequestMapping annotation = method.getAnnotationsByType(RequestMapping.class)[0];
        String uri = request.getUri();
        String path = annotation.path();

        int ind;
        try {
            ind = uri.indexOf(path.substring(0, path.substring(1).indexOf("/")));
        } catch (IndexOutOfBoundsException ex) {
            return null;
        }

        String response = matcher.getPathVariable(uri.substring(ind), path, annotation.placeholder());

        try {
            return response == null ? null : URLDecoder.decode(response, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return response;
        }
    }

}
