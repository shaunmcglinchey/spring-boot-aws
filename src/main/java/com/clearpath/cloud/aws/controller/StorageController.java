package com.clearpath.cloud.aws.controller;

import com.clearpath.cloud.aws.model.StorageRequest;
import com.clearpath.cloud.aws.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.UUID;

@RestController
public class StorageController {

    private StorageService storageService;

    @Value("${aws.bucket}")
    private String bucket;

    @Autowired
    public StorageController(StorageService storageService) {
        this.storageService = storageService;
    }

    @PostMapping("/upload")
    public ResponseEntity upload(@RequestBody StorageRequest request) {
        String uuid = UUID.randomUUID().toString();
        this.storageService.putObject(bucket, uuid, new File("src/main/resources/foobar.txt"));
        return new ResponseEntity<>(uuid, HttpStatus.CREATED);
    }
}
