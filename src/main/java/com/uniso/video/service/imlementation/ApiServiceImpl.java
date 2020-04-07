package com.uniso.video.service.imlementation;

import com.uniso.video.sdk.Client;
import com.uniso.video.sdk.domain.exception.ResponseException;
import com.uniso.video.sdk.domain.video.Video;
import com.uniso.video.sdk.domain.video.VideoInput;
import com.uniso.video.service.ApiService;
import com.uniso.video.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Optional;

@Service
public class ApiServiceImpl implements ApiService {

    private final Client client;

    private final StorageService storageService;

    @Autowired
    public ApiServiceImpl(Client client, StorageService storageService) {
        this.client = client;
        this.storageService = storageService;
    }


    @Override
    public Optional<Video> upload(VideoInput videoInput, MultipartFile file) {
        Optional<Video> optionalVideo = Optional.empty();
        try {
            String filePath = storageService.store(file);
            Video video = client.videos.upload(new File(filePath),videoInput);
            optionalVideo = Optional.of(video);
        } catch (ResponseException e) {
            //todo add log
            e.printStackTrace();
        } finally {
            return optionalVideo;
        }
    }
}
