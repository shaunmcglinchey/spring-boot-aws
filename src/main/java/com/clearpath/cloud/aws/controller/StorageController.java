package com.clearpath.cloud.aws.controller;

import com.clearpath.cloud.aws.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;

@RestController
public class StorageController {

    private StorageService storageService;

    @Autowired
    public StorageController(StorageService storageService) {
        this.storageService = storageService;
    }

    @PostMapping("/upload")
    public ResponseEntity upload() {
        this.storageService.putObject("storage-bucket", "storage-key", new File(""));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
