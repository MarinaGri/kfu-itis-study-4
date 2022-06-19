package ru.itis.framework.utils;


import org.springframework.stereotype.Component;

@Component
public class PathPatternMatcherImpl implements PathPatternMatcher {

    @Override
    public boolean isMatch(String path, String pattern, String placeholder) {
        return getCleanPath(path, pattern, placeholder).equals(getCleanPattern(pattern, placeholder));
    }

    @Override
    public String getPathVariable(String path, String pattern, String placeholder) {
        int[] indexes = getVariableIndexes(path, pattern, placeholder);

        return indexes == null ? null : path.substring(indexes[0], indexes[1]);
    }

    private String getCleanPath(String path, String pattern, String placeholder) {
        int[] indexes = getVariableIndexes(path, pattern, placeholder);

        return indexes == null ? path : path.substring(0, indexes[0]) + path.substring(indexes[1]);
    }

    private String getCleanPattern(String pattern, String placeholder) {
        return pattern.replace(placeholder, "");
    }

    private int[] getVariableIndexes(String path, String pattern, String placeholder) {
        if (placeholder.equals("") || !pattern.contains(placeholder)) {
            return null;
        }

        int start = pattern.indexOf(placeholder);

        if (!path.startsWith(pattern.substring(0, start))) {
            return null;
        }

        String firstPart = path.substring(0, start);
        String secondPart = path.substring(start);

        int end = secondPart.indexOf("/");
        end = end == -1 ? path.length() : end + firstPart.length();
        return new int[]{start, end};
    }

}
