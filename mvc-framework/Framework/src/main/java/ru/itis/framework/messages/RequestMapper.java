package ru.itis.framework.messages;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.stream.Collectors;

public class RequestMapper {

    public static Request from(HttpServletRequest request) {
        Enumeration<String> headerNames = request.getHeaderNames();

        List<Header> headers = Collections.list(headerNames)
                .stream()
                .map(name -> new Header(name, request.getHeader(name)))
                .collect(Collectors.toList());

        String body;
        try {
            body = request.getReader().lines().collect(Collectors.joining());
        } catch (IOException ex) {
            body = null;
        }

        return Request.builder()
                .method(RequestMethod.valueOf(request.getMethod()))
                .uri(request.getRequestURI())
                .parameters(request.getParameterMap())
                .headers(headers)
                .body(body)
                .build();
    }
}
