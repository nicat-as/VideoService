package com.uniso.video.controller;

import com.uniso.video.sdk.domain.video.Video;
import com.uniso.video.sdk.domain.video.VideoInput;
import com.uniso.video.service.ApiService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("api")
public class ApiController {

    private final ApiService apiService;

    public ApiController(ApiService apiService) {
        this.apiService = apiService;
    }


    @PostMapping("/upload")
    public ResponseEntity<?> upload(@RequestPart("videoFile") MultipartFile file,
                                    @RequestPart("videoInput") String videoInput) {
        ResponseEntity responseEntity = new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        Optional<Video> optionalVideo = apiService.upload(videoInput, file);
        if (optionalVideo.isPresent()) {
            responseEntity = new ResponseEntity(optionalVideo.get(), HttpStatus.OK);
        }
        return responseEntity;
    }

    @PostMapping("/test")
    public ResponseEntity<?> test(@RequestBody VideoInput videoInput) {
        return new ResponseEntity<>(videoInput, HttpStatus.OK);
    }

}
