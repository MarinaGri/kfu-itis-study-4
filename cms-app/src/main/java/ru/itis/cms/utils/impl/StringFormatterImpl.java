package ru.itis.cms.utils.impl;

import org.springframework.stereotype.Component;
import ru.itis.cms.utils.StringFormatter;

import java.util.Map;

@Component
public class StringFormatterImpl implements StringFormatter {

    private String patternStart = "${";

    private String patternEnd = "}";


    @Override
    public String format(StringBuilder builder, Map<String, Object> map) {

        for (Map.Entry<String, Object> entry : map.entrySet()) {

            int start;
            String pattern = patternStart + entry.getKey() + patternEnd;

            String value = entry.getValue() == null ? "null" : entry.getValue().toString();

            while ((start = builder.indexOf(pattern)) != -1) {
                builder.replace(start, start + pattern.length(), value);
            }

        }

        return builder.toString();
    }

    @Override
    public void setPatternStart(String pattern) {
        this.patternStart = pattern;
    }

    @Override
    public void setPatternEnd(String pattern) {
        this.patternEnd = pattern;
    }
}
