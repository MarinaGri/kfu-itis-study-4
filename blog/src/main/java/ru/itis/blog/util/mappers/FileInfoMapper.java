package ru.itis.blog.util.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import ru.itis.blog.dto.response.FileLink;
import ru.itis.blog.models.FileInfo;

import javax.servlet.http.HttpServletRequest;

@Mapper(componentModel = "spring")
public abstract class FileInfoMapper {

    @Autowired
    private HttpServletRequest request;

    public FileLink toResponse(FileInfo fileInfo){

        if(fileInfo != null) {
            String host = request.getHeader("Host");

            String link = host + request.getContextPath() +
                    request.getRequestURI() + fileInfo.getStorageFileName();
            return new FileLink(link);
        }

        return null;
    }
}
