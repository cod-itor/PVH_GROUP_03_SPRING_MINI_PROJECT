package org.ksga.pvh_group_03_spring_mini_project.controller;

import lombok.RequiredArgsConstructor;

import org.ksga.pvh_group_03_spring_mini_project.service.S3FileService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/files")
@RequiredArgsConstructor
public class S3FileController {

    private final S3FileService s3FileService;


}