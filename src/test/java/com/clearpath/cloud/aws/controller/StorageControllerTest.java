package com.clearpath.cloud.aws.controller;

import com.clearpath.cloud.aws.exception.UploadFailedException;
import com.clearpath.cloud.aws.service.StorageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.io.File;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(StorageController.class)
public class StorageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StorageService storageService;

    private final String storageRequestBody = "{\"name\":\"Bob\"}";

    @Test
    public void whenUploadFailed_thenResponseStatusIs500InternalServerError() throws Exception {
        when(storageService.putObject(anyString(), anyString(), any(File.class)))
                .thenThrow(UploadFailedException.class);
        mockMvc.perform(post("/upload")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(storageRequestBody))
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void whenUploadSucceeds_thenResponseStatusIs201Created() throws Exception {
        when(storageService.putObject(anyString(), anyString(), any(File.class)))
                .thenReturn(PutObjectResponse.builder().build());
        mockMvc.perform(post("/upload")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(storageRequestBody))
                .andExpect(status().isCreated());
    }
}
