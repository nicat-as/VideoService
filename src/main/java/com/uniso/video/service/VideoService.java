package com.uniso.video.service;

import com.uniso.video.sdk.domain.QueryParams;
import com.uniso.video.sdk.domain.video.Status;
import com.uniso.video.sdk.domain.video.Video;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface VideoService {

    Optional<Video> upload(String videoInput, MultipartFile file);

    Optional<Status> getStatus(String status);

    List<Video> getVideos();

    List<Video> getVideosByQuery(QueryParams params);

    Optional<Video> showVideo(String videoId);

    Optional<Video> uploadThumbnail(String videoId, MultipartFile image);

    Optional<Video> pickThumbnail(String videoId, String pattern);

}
