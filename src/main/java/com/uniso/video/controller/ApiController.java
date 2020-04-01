package com.uniso.video.controller;

import com.uniso.video.sdk.Client;
import com.uniso.video.sdk.ClientFactory;
import com.uniso.video.sdk.domain.video.Video;
import com.uniso.video.sdk.domain.video.VideoInput;
import com.uniso.video.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@RestController
@RequestMapping("api")
public class ApiController {

    @Value("${api.video.sandbox}")
    private String sandboxKey;

    @Autowired
    private StorageService storageService;

    @PostMapping("/upload")
    public ResponseEntity<?> upload(@RequestParam(name = "file") MultipartFile file){
        Video video = null;
        String filePath;
        try {
            filePath = storageService.store(file);
            Client client = new ClientFactory().createSandbox(sandboxKey);
            video = client.videos.upload(new File(filePath));
            storageService.deleteFile(filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            return new ResponseEntity(video.videoId, HttpStatus.OK);
        }
    }
}
