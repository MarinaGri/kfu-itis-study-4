package ru.itis.cms.aspects;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import ru.itis.cms.utils.StringFormatter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Aspect
@Component
public class LogAspect {

    private final StringFormatter stringFormatter;

    @Before("@annotation(ru.itis.cms.aspects.Loggable)")
    public void logMethod(JoinPoint joinPoint) {
        LocalDateTime dateTime = LocalDateTime.now();

        StringBuilder builder = new StringBuilder();
        Map<String, Object> map = new HashMap<>();

        setMethodInfo(joinPoint, builder, map);
        setTimeInfo(dateTime, builder, map);
        setUserInfo(builder, map);

        log.info(stringFormatter.format(builder, map));
    }

    private void setMethodInfo(JoinPoint joinPoint, StringBuilder builder, Map<String, Object> map) {
        builder.append("Method ${method}(");
        map.put("method", joinPoint.getSignature().getName());

        Object[] args = joinPoint.getArgs();

        int i = 0;
        for (Object arg : args) {
            map.put(String.valueOf(i), arg);
            builder.append("${").append(i).append("}, ");
            i++;
        }

        builder.delete(builder.length() - 2, builder.length());
        builder.append(") ");
    }

    private void setTimeInfo(LocalDateTime dateTime, StringBuilder builder, Map<String, Object> map) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        map.put("date-time", dateTime.format(formatter));
        builder.append("at ${date-time} ");
    }

    private void setUserInfo(StringBuilder builder, Map<String, Object> map) {
        try {
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
                    .getAuthentication().getPrincipal();

            builder.append(" Authorized user: ${user}");
            map.put("user", userDetails.getUsername());
        } catch (Throwable ex) {
            builder.append(" No authorized user");
        }
    }
}
