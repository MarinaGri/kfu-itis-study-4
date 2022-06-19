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
public class Request {
    private RequestMethod method;
    private String uri;
    private List<Header> headers;
    private Map<String, String[]> parameters;
    private String body;
}
