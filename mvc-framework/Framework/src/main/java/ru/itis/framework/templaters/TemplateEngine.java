package ru.itis.framework.templaters;

import ru.itis.framework.exceptions.ResourceNotFoundException;

import javax.servlet.ServletContext;
import java.util.Locale;
import java.util.Map;

public interface TemplateEngine {

    String process(String viewName, Map<String, Object> model) throws ResourceNotFoundException;

    void setServletContext(ServletContext context);

    void setPrefix(String prefix);
    void setSuffix(String suffix);

    void setLocale(Locale locale);
    void setEncoding(String encoding);

}
