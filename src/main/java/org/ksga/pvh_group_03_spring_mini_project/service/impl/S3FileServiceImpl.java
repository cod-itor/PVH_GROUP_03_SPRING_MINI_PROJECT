package org.ksga.pvh_group_03_spring_mini_project.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.ksga.pvh_group_03_spring_mini_project.service.S3FileService;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.S3Client;

@Service
@RequiredArgsConstructor
public class S3FileServiceImpl implements S3FileService {

    private final S3Client s3Client;
    @Value("${rustfs.bucket.name}")
    private String bucketName;


}
