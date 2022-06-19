package ru.itis.blog.services;

import org.springframework.web.multipart.MultipartFile;
import ru.itis.blog.models.FileInfo;


public interface FileService {

    FileInfo upload(MultipartFile multipart);

}
