package com.uniso.video.controller;

import com.uniso.video.sdk.Client;
import com.uniso.video.sdk.domain.video.Video;
import com.uniso.video.service.AsyncService;
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
import java.util.concurrent.Future;

@RestController
@RequestMapping("api")
public class ApiController {

    @Value("${api.video.sandbox}")
    private String sandboxKey;

    private final Client client;

    private final StorageService storageService;
    private final AsyncService asyncService;


    @Autowired
    public ApiController(Client client, StorageService storageService, AsyncService asyncService) {
        this.client = client;
        this.storageService = storageService;
        this.asyncService = asyncService;
    }

    @PostMapping("/upload")
    public ResponseEntity<?> upload(@RequestParam(name = "file") MultipartFile[] file) {
        StringBuilder videoID = new StringBuilder();
        try {
            for (MultipartFile f : file) {
                Future<String> stringFuture = asyncService.execute(f);
//                if (stringFuture.isDone()){
                    videoID.append(stringFuture.get());
//                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return new ResponseEntity(videoID, HttpStatus.OK);
        }
    }

    @RequestMapping("/test")
    public ResponseEntity<?> test(@RequestParam(name = "name") String name){
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
