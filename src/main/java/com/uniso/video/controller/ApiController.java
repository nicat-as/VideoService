package com.uniso.video.controller;

import com.uniso.video.domain.VideoResponse;
import com.uniso.video.sdk.domain.QueryParams;
import com.uniso.video.sdk.domain.video.Status;
import com.uniso.video.sdk.domain.video.Video;
import com.uniso.video.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("api")
public class ApiController {

    private final VideoService videoService;

    @Autowired
    public ApiController(VideoService videoService) {
        this.videoService = videoService;
    }


    @PostMapping("/upload")
    public ResponseEntity<?> upload(@RequestPart("videoFile") MultipartFile file,
                                    @RequestPart("videoInput") String videoInput) throws InterruptedException {
        ResponseEntity responseEntity = new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        Optional<Video> optionalVideo = videoService.upload(videoInput, file);
        if (optionalVideo.isPresent()) {
            Status status;
            do {
                status = videoService.getStatus(optionalVideo.get().videoId).get();
                Thread.sleep(100);
            } while (!status.encoding.playable);
            VideoResponse response = new VideoResponse(optionalVideo.get(), status);
            responseEntity = new ResponseEntity(response, HttpStatus.OK);
        }
        return responseEntity;
    }

    @GetMapping("/video/status/{videoId}")
    public ResponseEntity<?> getVideoStatus(@PathVariable String videoId) {
        Optional<Status> optionalStatus = videoService.getStatus(videoId);
        Status status = optionalStatus.orElseThrow(() -> new RuntimeException("Not getting status"));
        return new ResponseEntity<>(status, HttpStatus.OK);
    }

    @GetMapping("/videos")
    public ResponseEntity<?> getVideosList() {
        List<Video> list = videoService.getVideos();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping("/videos/query")
    public ResponseEntity<?> getVideosByQuery(@RequestBody QueryParams params) {
        List<Video> list = videoService.getVideosByQuery(params);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }


}
