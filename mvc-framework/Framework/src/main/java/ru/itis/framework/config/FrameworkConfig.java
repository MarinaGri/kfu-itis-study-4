package ru.itis.framework.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


@Configuration
@ComponentScan({"ru.itis.framework.converters", "ru.itis.framework.invokers",
        "ru.itis.framework.loaders", "ru.itis.framework.routing",
        "ru.itis.framework.templaters", "ru.itis.framework.utils"})
public class FrameworkConfig {
}
