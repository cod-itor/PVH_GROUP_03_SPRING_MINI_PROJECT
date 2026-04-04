package org.ksga.pvh_group_03_spring_mini_project.service;


import jakarta.validation.Valid;
import org.ksga.pvh_group_03_spring_mini_project.model.entity.FileMetadata;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface S3FileService {

    FileMetadata uploadFile(@Valid MultipartFile file);

    Resource getFileByFileName(@Valid String fileName);
}
