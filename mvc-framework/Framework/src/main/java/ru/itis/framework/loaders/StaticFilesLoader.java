package ru.itis.framework.loaders;

import org.springframework.stereotype.Component;
import ru.itis.framework.exceptions.ResourceNotFoundException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Component
public class StaticFilesLoader implements StaticResourceLoader {

    private String[] folders;

    @Override
    public void setFolders(String[] folders) {
        this.folders = folders;
    }

    @Override
    public boolean isStaticResource(String path) {
        if (folders == null) {
            return false;
        }

        for (String folder : folders) {
            if (path.contains(folder)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void load(String path, HttpServletResponse response) throws ResourceNotFoundException {

        if (Files.exists(Paths.get(path))) {
            try {
                response.setContentType(Files.probeContentType(Paths.get(path)));
                Files.copy(Paths.get(path), response.getOutputStream());
            } catch (IOException ex) {
                throw new ResourceNotFoundException("Cant load " + path, ex);
            }
        } else {
            throw new ResourceNotFoundException("File with path: " + path + " does not exist");
        }

    }

}
