package ru.itis.framework.routing;

import org.springframework.context.ApplicationContext;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;


public interface RequestMapping {

    void setContext(ApplicationContext context);

    void initRoutes();

    Optional<HandlerInfo> getHandler(HttpServletRequest request);
}
