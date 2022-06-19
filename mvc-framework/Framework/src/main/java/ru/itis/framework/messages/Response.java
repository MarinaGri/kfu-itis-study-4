package ru.itis.framework.messages;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Response {
    private Integer status;
    private List<Header> headers;

    private String viewName;
    private Map<String, Object> model;

    private String body;
}
