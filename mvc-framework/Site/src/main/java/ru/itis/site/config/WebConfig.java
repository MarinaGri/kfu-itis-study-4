package ru.itis.site.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ru.itis.framework.loaders.StaticResourceLoader;
import ru.itis.framework.loaders.StaticFilesLoader;
import ru.itis.framework.templaters.FreemarkerEngine;
import ru.itis.framework.templaters.TemplateEngine;


@Configuration
@ComponentScan({"ru.itis.site.controllers"})
public class WebConfig {

    @Bean
    public TemplateEngine templateEngine(){
        TemplateEngine templateEngine = new FreemarkerEngine();
        templateEngine.setSuffix(".ftlh");
        templateEngine.setPrefix("/WEB-INF/templates/");
        return templateEngine;
    }

    @Bean
    public StaticResourceLoader resourceLoader() {
        StaticResourceLoader loader = new StaticFilesLoader();
        String[] folders = {"/assets"};
        loader.setFolders(folders);
        return loader;
    }

}
