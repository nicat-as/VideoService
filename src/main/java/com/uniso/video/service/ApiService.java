package com.uniso.video.service;

import com.uniso.video.sdk.domain.video.Video;
import com.uniso.video.sdk.domain.video.VideoInput;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface ApiService {

    Optional<Video> upload(VideoInput videoInput, MultipartFile file);
}
