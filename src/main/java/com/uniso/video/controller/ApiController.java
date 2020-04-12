package com.uniso.video.controller;

import com.uniso.video.domain.VideoResponse;
import com.uniso.video.sdk.domain.QueryParams;
import com.uniso.video.sdk.domain.video.Status;
import com.uniso.video.sdk.domain.video.Video;
import com.uniso.video.service.VideoService;
import io.swagger.annotations.*;
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
@Api(value = "Video Service Api methods", produces = "application/json")
public class ApiController {

    private final VideoService videoService;

    @Autowired
    public ApiController(VideoService videoService) {
        this.videoService = videoService;
    }


    @ApiOperation(value = "Upload Video file with video Information"
            , response = VideoResponse.class
            , notes = "Add multipart file and about information. You will get information about video and video status")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list", response = VideoResponse.class)
            , @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden")
            , @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
            , @ApiResponse(code = 500, message = "Server Error")
    })
    @PostMapping("/upload")
    public ResponseEntity<?> upload(
            @ApiParam(value = "Video file for uploading", required = true)
            @RequestPart("videoFile") MultipartFile file,
            @ApiParam(value = "Information About video file", required = true)
            @RequestPart("videoInput") String videoInput
    ) throws InterruptedException {
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


    @ApiOperation(value = "Getting video status"
            , response = Status.class
            , notes = "Getting video duration, resolution from uploaded video")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list", response = Status.class)
            , @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden")
            , @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
            , @ApiResponse(code = 500, message = "Server Error")
    })
    @GetMapping("/video/status/{videoId}")
    public ResponseEntity<?> getVideoStatus(
            @ApiParam(value = "Id of uploaded video", required = true)
            @PathVariable String videoId
    ) {
        Optional<Status> optionalStatus = videoService.getStatus(videoId);
        Status status = optionalStatus.orElseThrow(() -> new RuntimeException("Not getting status"));
        return new ResponseEntity<>(status, HttpStatus.OK);
    }

    @ApiOperation(value = "Getting video list in api.video"
            , response = Video.class
            , responseContainer = "List"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list", response = Video.class, responseContainer = "List")
            , @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden")
            , @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
            , @ApiResponse(code = 500, message = "Server Error")
    })
    @GetMapping("/videos")
    public ResponseEntity<?> getVideosList() {
        List<Video> list = videoService.getVideos();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @ApiOperation(value = "Getting video list in api.video"
            , response = Video.class
            , responseContainer = "List"
            ,notes = "This endpoint will return information about video through query parameters"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list", response = Video.class, responseContainer = "List")
            , @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden")
            , @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
            , @ApiResponse(code = 500, message = "Server Error")
    })
    @PostMapping("/videos/query")
    public ResponseEntity<?> getVideosByQuery(
            @ApiParam(value = "Querying video",required = true)
            @RequestBody QueryParams params) {
        List<Video> list = videoService.getVideosByQuery(params);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }


}
