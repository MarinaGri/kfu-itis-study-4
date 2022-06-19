package ru.itis.framework.routing;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import ru.itis.framework.exceptions.NotInitializedContextException;
import ru.itis.framework.messages.RequestDto;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.*;

import static ru.itis.framework.messages.RequestDtoMapper.from;

@Component
public abstract class AbstractRequestMapping implements RequestMapping {

    protected ApplicationContext context;

    private final Map<RequestDto, Method> requestMap;
    private final Map<Method, Object> routeInfo;

    public AbstractRequestMapping() {
        this.requestMap = new HashMap<>();
        this.routeInfo = new HashMap<>();
    }

    protected abstract List<Class<?>> getHandlers();

    protected abstract boolean isRelevantMethod(Method method);

    protected abstract RequestDto getRequest(Method method);

    protected abstract HandlerInfo createRouteInfo(Object handler, Method method);

    protected abstract boolean arePathsEqual(Method method, String methodPath, String path);

    @Override
    public void setContext(ApplicationContext context) {
        this.context = context;
    }

    @Override
    public void initRoutes() {
        if (context == null) {
            throw new NotInitializedContextException("Context doesn't have to be null");
        }
        fillMaps(getHandlers());
    }

    @Override
    public Optional<HandlerInfo> getHandler(HttpServletRequest request) {
        if (!routeInfo.isEmpty()) {
            Method method = getMethod(request);
            if (method != null) {
                return Optional.of(createRouteInfo(routeInfo.get(method), method));
            }
        }
        return Optional.empty();
    }

    private Method getMethod(HttpServletRequest servletRequest) {
        RequestDto comparable = from(servletRequest);
        for (RequestDto request : requestMap.keySet()) {
            if (request.getMethod().equals(comparable.getMethod())) {
                Method method = requestMap.get(request);
                if (arePathsEqual(method, request.getPath(), comparable.getPath())) {
                    return method;
                }
            }
        }
        return null;
    }

    private void fillMaps(List<Class<?>> controllers) {
        for (Class<?> controller : controllers) {
            Method[] methods = controller.getMethods();

            for (Method method : methods) {

                if (isRelevantMethod(method)) {
                    requestMap.put(getRequest(method), method);
                    routeInfo.put(method, context.getBean(controller));
                }
            }

        }
    }

}
