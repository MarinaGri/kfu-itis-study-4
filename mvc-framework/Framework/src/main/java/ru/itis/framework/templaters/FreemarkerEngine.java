package ru.itis.framework.templaters;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.stereotype.Component;
import ru.itis.framework.exceptions.NotInitializedContextException;
import ru.itis.framework.exceptions.ResourceNotFoundException;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Locale;
import java.util.Map;

@Component
public class FreemarkerEngine implements TemplateEngine {

    private String prefix = "";
    private String suffix = "";

    private ServletContext servletContext;

    private Configuration configuration;

    private Locale locale;

    private String encoding;

    @Override
    public String process(String viewName, Map<String, Object> model) throws ResourceNotFoundException {

        Template template;
        Writer writer = new StringWriter();

        try {
            configure();

            template = configuration.getTemplate(viewName + suffix);

            template.process(model, writer);

        } catch (TemplateException | IOException ex) {
            throw new ResourceNotFoundException("Cant process: " + viewName +
                    " with prefix: " + prefix + " and suffix: " + suffix, ex);
        }

        return writer.toString();
    }

    private void configure() throws IOException {
        if (servletContext == null) {
            throw new NotInitializedContextException("ServletContext doesn't have to be null");
        }

        if (configuration == null) {

            configuration = new Configuration(Configuration.VERSION_2_3_21);
            configuration.setDefaultEncoding(encoding == null ? "UTF-8" : encoding);

            if (locale != null) {
                configuration.setLocale(locale);
            }

            configuration.setDirectoryForTemplateLoading(
                    new File(servletContext.getRealPath("") + prefix));
        }
    }

    @Override
    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    @Override
    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    @Override
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    @Override
    public void setServletContext(ServletContext context) {
        this.servletContext = context;
    }

}
