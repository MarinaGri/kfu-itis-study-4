package ru.itis.app;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import ru.itis.config.ApplicationConfig;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

public class ApplicationInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) {
        AnnotationConfigWebApplicationContext springWebContext = new AnnotationConfigWebApplicationContext();
        springWebContext.register(ApplicationConfig.class);
        springWebContext.getEnvironment().setActiveProfiles(System.getenv().get("SPRING_PROFILE"));
        ContextLoaderListener listener = new ContextLoaderListener(springWebContext);
        servletContext.addListener(listener);

        ServletRegistration.Dynamic dispatcherServlet = servletContext.addServlet("dispatcher",
                new DispatcherServlet(springWebContext));
        dispatcherServlet.setLoadOnStartup(1);
        dispatcherServlet.addMapping("/");
    }
}
