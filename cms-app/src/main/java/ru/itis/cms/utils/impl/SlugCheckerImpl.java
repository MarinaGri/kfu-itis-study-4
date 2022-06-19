package ru.itis.cms.utils.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.PathPatternsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.util.pattern.PathPattern;
import ru.itis.cms.aspects.Loggable;
import ru.itis.cms.utils.SlugChecker;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Set;

@RequiredArgsConstructor
@Component
public class SlugCheckerImpl implements SlugChecker {

    private final RequestMappingHandlerMapping handlerMapping;

    private final HttpSecurity httpSecurity;

    @Loggable
    @Override
    public boolean isEqualToSecurityPaths(String slug) {
        LogoutConfigurer<?> configurer = httpSecurity.getConfigurer(LogoutConfigurer.class);
        Field logoutUrl = ReflectionUtils.findField(LogoutConfigurer.class, "logoutUrl");
        if (logoutUrl != null) {
            logoutUrl.setAccessible(true);
            String field = (String) ReflectionUtils.getField(logoutUrl, configurer);
            if (field != null) {
                return slug.equals(field.substring(1));
            }
        }
        return false;
    }

    @Override
    public boolean isEqualToControllersPaths(String slug) {
        Map<RequestMappingInfo, HandlerMethod> handlerMethods = handlerMapping.getHandlerMethods();

        for (RequestMappingInfo info : handlerMethods.keySet()) {
            PathPatternsRequestCondition pathPatterns = info.getPathPatternsCondition();
            if (pathPatterns != null) {
                Set<PathPattern> patterns = pathPatterns.getPatterns();
                for (PathPattern pattern : patterns) {
                    if (pattern.getPatternString().substring(1).equals(slug)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

}
