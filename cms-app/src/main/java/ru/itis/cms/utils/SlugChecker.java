package ru.itis.cms.utils;

public interface SlugChecker {

    boolean isEqualToSecurityPaths(String slug);

    boolean isEqualToControllersPaths(String slug);

}
