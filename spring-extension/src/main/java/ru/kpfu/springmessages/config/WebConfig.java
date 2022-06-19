package ru.kpfu.springmessages.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import ru.kpfu.springmessages.message_source.DbMessageSource;
import ru.kpfu.springmessages.resolver.HostLocaleResolver;

@Configuration
@EnableWebMvc
@ComponentScan("ru.kpfu.springmessages.controller")
@EnableTransactionManagement
public class WebConfig extends WebMvcConfigurerAdapter {

    @Bean
    public ViewResolver setupViewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/jsp/");
        resolver.setSuffix(".jsp");
        resolver.setViewClass(JstlView.class);
        resolver.setRedirectContextRelative(false);
        return resolver;
    }

    @Bean
    public LocaleResolver localeResolver() {
        return new HostLocaleResolver();
    }

    @Bean
    public MessageSource messageSource() {
        DbMessageSource res = new DbMessageSource();
        res.setBasenames("messages");
        res.setDefaultEncoding("UTF-8");
        res.setUseCodeAsDefaultMessage(false);
        return res;
    }

}
