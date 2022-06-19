package ru.itis.cms.utils;

import java.util.Map;

public interface StringFormatter {

    String format(StringBuilder builder, Map<String, Object> map);

    void setPatternStart(String pattern);

    void setPatternEnd(String pattern);

}
