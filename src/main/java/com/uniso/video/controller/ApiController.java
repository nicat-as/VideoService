package com.uniso.video.controller;

import com.uniso.video.sdk.Client;
import com.uniso.video.sdk.ClientFactory;
import com.uniso.video.sdk.domain.video.Video;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;

@RestController
@RequestMapping("api")
public class ApiController {

    @PostMapping("/upload")
    public ResponseEntity<?> upload(@RequestParam(name = "path") String path){
        Video video = null;
        try {
            Client client = new ClientFactory().createSandbox("Xc1YmjmUzqNkTtdXQDtVyDQeB5sF1fPtoit5scz5pWw");
            if (path.isEmpty()){
                path="/home/nicat/Documents/aws_con.mp4";
            }
            video = client.videos.upload(new File(path));
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            return new ResponseEntity(video.videoId, HttpStatus.OK);
        }
    }
}
