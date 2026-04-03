package org.ksga.pvh_group_03_spring_mini_project.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.ksga.pvh_group_03_spring_mini_project.model.entity.FileMetadata;
import org.ksga.pvh_group_03_spring_mini_project.model.response.ApiResponse;
import org.ksga.pvh_group_03_spring_mini_project.service.S3FileService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.time.LocalDateTime;

@RestController
@RequestMapping("api/v1/files")
@RequiredArgsConstructor
public class S3FileController {
    private final S3FileService s3FileService;

    @PostMapping(value = "/upload-file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Upload a file")
    public ResponseEntity<ApiResponse<FileMetadata>> uploadFile(@RequestParam @Valid MultipartFile file) {

        FileMetadata fileMetadata = s3FileService.uploadFile(file);

        ApiResponse<FileMetadata> response = ApiResponse.<FileMetadata>builder()
                .success(true)
                .status(HttpStatus.CREATED)
                .message("File uploaded successfully to RustFS")
                .payload(fileMetadata)
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/preview-file/{file-name}")
    @Operation(summary = "Preview a file")
    public ResponseEntity<Resource> getFileByFileName(@PathVariable("file-name") String fileName) {
        Resource resource  = s3FileService.getFileByFileName(fileName);

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(resource);
    }

//    @GetMapping("/download-file/{file-name}")
//    public ResponseEntity<Resource> downloadFileByFileName(@PathVariable("file-name") String fileName) {
//        Resource resource = s3FileService.getFileByFileName(fileName);
//
//        return ResponseEntity.ok()
//                .contentType(MediaType.APPLICATION_OCTET_STREAM)
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
//                .body(resource);
//    }

}