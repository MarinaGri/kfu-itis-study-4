package ru.itis.framework.messages;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestDto {
    private RequestMethod method;
    private String path;
    private Map<String, String[]> parameters;

}
