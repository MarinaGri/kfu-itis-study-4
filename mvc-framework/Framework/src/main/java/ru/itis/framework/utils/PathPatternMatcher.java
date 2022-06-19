package ru.itis.framework.utils;

public interface PathPatternMatcher {

    boolean isMatch(String path, String pattern, String placeholder);

    String getPathVariable(String path, String pattern, String placeholder);

}
