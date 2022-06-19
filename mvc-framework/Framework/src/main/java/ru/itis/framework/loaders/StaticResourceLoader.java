package ru.itis.framework.loaders;

import ru.itis.framework.exceptions.ResourceNotFoundException;

import javax.servlet.http.HttpServletResponse;

public interface StaticResourceLoader {

    void setFolders(String[] folders);

    boolean isStaticResource(String path);

    void load(String path, HttpServletResponse response) throws ResourceNotFoundException;
}
