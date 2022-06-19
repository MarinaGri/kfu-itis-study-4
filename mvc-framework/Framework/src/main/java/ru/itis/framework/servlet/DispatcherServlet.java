package ru.itis.framework.servlet;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.itis.framework.config.FrameworkConfig;
import ru.itis.framework.exceptions.ResourceNotFoundException;
import ru.itis.framework.loaders.StaticResourceLoader;
import ru.itis.framework.messages.Header;
import ru.itis.framework.messages.Response;
import ru.itis.framework.templaters.TemplateEngine;
import ru.itis.framework.invokers.*;
import ru.itis.framework.routing.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import java.io.IOException;
import java.util.*;

import static ru.itis.framework.messages.RequestMapper.from;

/**
 * Central dispatcher for HTTP request handlers/controllers.
 * The DispatcherServlet, as any Servlet, needs to be declared and
 * mapped according to the Servlet specification.
 * It can be done in {@link javax.servlet.ServletContextListener} implementation
 * by using {@link ServletRegistration.Dynamic}
 */

public class DispatcherServlet extends HttpServlet {

    private final String NOT_FOUND_MESS = "Resource not found";

    private final ApplicationContext context;

    private final ApplicationContext frameworkContext;

    private RequestMapping requestMapping;

    private StaticResourceLoader staticLoader;

    private TemplateEngine templateEngine;

    private List<MethodInvoker> invokers;


    public DispatcherServlet(ApplicationContext context) {
        this.context = context;
        this.frameworkContext = new AnnotationConfigApplicationContext(FrameworkConfig.class);
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        initStaticLoader();
        initRequestMapping();
        initTemplateEngine();
        initInvokers();
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            Response result = doDispatch(request, response);

            if (result != null) {
                processResponse(result, response);
            }

        } catch (ResourceNotFoundException ex) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().println(NOT_FOUND_MESS);
        }
    }

    private Response doDispatch(HttpServletRequest request, HttpServletResponse response)
            throws ResourceNotFoundException {

        String cleanRequestPath = request.getRequestURI().substring(request.getContextPath().length());

        if (staticLoader.isStaticResource(cleanRequestPath)) {
            staticLoader.load(getServletContext().getRealPath(cleanRequestPath), response);
            return null;

        } else {
            Optional<HandlerInfo> candidate = requestMapping.getHandler(request);

            if (candidate.isPresent()) {
                HandlerInfo route = candidate.get();

                for (MethodInvoker invoker : invokers) {
                    if (invoker.canHandle(route.getMethod())) {
                        return invoker.invoke(route, from(request));
                    }
                }
            }

            throw new ResourceNotFoundException(NOT_FOUND_MESS);
        }
    }

    private void processResponse(Response result, HttpServletResponse response)
            throws ResourceNotFoundException, IOException {

        if (result.getStatus() != null) {
            response.setStatus(result.getStatus());
        }

        if (result.getHeaders() != null) {
            for (Header header : result.getHeaders()) {
                response.setHeader(header.getName(), header.getValue());
            }
        }

        String viewName = result.getViewName();
        String body = result.getBody();

        if (viewName != null) {
            body = templateEngine.process(viewName, result.getModel());
        }

        if (body != null) {
            response.getWriter().println(body);
        }

    }

    private void initStaticLoader() {
        try {
            this.staticLoader = context.getBean(StaticResourceLoader.class);
        } catch (NoSuchBeanDefinitionException ex) {
            this.staticLoader = frameworkContext.getBean(StaticResourceLoader.class);
        }
    }

    private void initTemplateEngine() {
        try {
            this.templateEngine = context.getBean(TemplateEngine.class);
        } catch (NoSuchBeanDefinitionException ex) {
            this.templateEngine = frameworkContext.getBean(TemplateEngine.class);
        }
        templateEngine.setServletContext(getServletContext());
    }

    private void initInvokers() {
        try {
            Map<String, MethodInvoker> beansOfType = context.getBeansOfType(MethodInvoker.class);

            if (!beansOfType.isEmpty()) {
                this.invokers = new ArrayList<>(beansOfType.values());
            } else {
                initDefaultInvokers();
            }
        } catch (BeansException ex) {
            initDefaultInvokers();
        }
    }

    private void initRequestMapping() {
        try {
            this.requestMapping = context.getBean(RequestMapping.class);
        } catch (NoSuchBeanDefinitionException ex) {
            this.requestMapping = frameworkContext.getBean(RequestMapping.class);
            requestMapping.setContext(context);
            requestMapping.initRoutes();
        }
    }

    private void initDefaultInvokers() {
        this.invokers = new ArrayList<>(frameworkContext.getBeansOfType(MethodInvoker.class).values());
    }

}
