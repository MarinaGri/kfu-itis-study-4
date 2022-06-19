package ru.itis.framework.messages;

import ru.itis.framework.annotaions.RequestMapping;

import javax.servlet.http.HttpServletRequest;

public class RequestDtoMapper {

    public static RequestDto from(RequestMapping requestMapping) {
        return RequestDto.builder()
                .method(requestMapping.method())
                .path(requestMapping.path())
                .build();
    }

    public static RequestDto from(HttpServletRequest request) {
        return RequestDto.builder()
                .method(RequestMethod.valueOf(request.getMethod()))
                .path(request.getRequestURI().substring(request.getContextPath().length()))
                .parameters(request.getParameterMap())
                .build();
    }
}
