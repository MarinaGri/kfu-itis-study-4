package ru.itis.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.blog.models.FileInfo;

import java.util.Optional;

public interface FilesInfoRepository extends JpaRepository<FileInfo, Long> {

    Optional<FileInfo> findByStorageFileName(String fileName);

}
