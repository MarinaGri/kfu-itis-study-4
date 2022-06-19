package ru.itis.blog.services.impl;

import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.itis.blog.exceptions.FileStorageException;
import ru.itis.blog.models.FileInfo;
import ru.itis.blog.repositories.FilesInfoRepository;
import ru.itis.blog.services.FileService;

import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class FileServiceImpl implements FileService {

    @Value("${files.storage.path}")
    private String storagePath;

    private final FilesInfoRepository filesInfoRepository;

    @Transactional
    @Override
    public FileInfo upload(MultipartFile multipart) {
        try {
            String extension = FilenameUtils.getExtension(multipart.getOriginalFilename());

            String storageFileName = UUID.randomUUID() + "." + extension;

            FileInfo file = FileInfo.builder()
                    .type(multipart.getContentType())
                    .originalFileName(multipart.getOriginalFilename())
                    .storageFileName(storageFileName)
                    .size(multipart.getSize())
                    .build();

            Files.copy(multipart.getInputStream(), Paths.get(storagePath, file.getStorageFileName()));

            return filesInfoRepository.save(file);
        } catch (IOException ex) {
            throw new FileStorageException(ex);
        }
    }

}
