package com.uniso.video.service.imlementation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.uniso.video.exception.StorageException;
import com.uniso.video.sdk.Client;
import com.uniso.video.sdk.domain.exception.ResponseException;
import com.uniso.video.sdk.domain.video.Video;
import com.uniso.video.sdk.domain.video.VideoInput;
import com.uniso.video.service.ApiService;
import com.uniso.video.service.StorageService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Optional;

@Service
public class ApiServiceImpl implements ApiService {
    private final Logger logger = LogManager.getLogger(ApiServiceImpl.class);

    @Autowired
    private Client client;

    @Autowired
    private StorageService storageService;


    @Override
    public Optional<Video> upload(String videoInput, MultipartFile file) {
        Optional<Video> optionalVideo = Optional.empty();
        try {
            String filePath = storageService.store(file);
            logger.info(Thread.currentThread().getName() + " filePath is : " + filePath);

            ObjectMapper mapper = new ObjectMapper();
            VideoInput input = mapper.readValue(videoInput,VideoInput.class);
            Video video = client.videos.upload(new File(filePath), input);
            logger.info(Thread.currentThread().getName() + " Video information : " + video);

            optionalVideo = Optional.of(video);
            logger.info(Thread.currentThread().getName() + " Deleting file from path : " + filePath);

            storageService.deleteFile(filePath);

        } catch (ResponseException e) {
            logger.error(Thread.currentThread().getName() + " Video not uploaded : " + e);
        } catch (StorageException e) {
            logger.error("Storage exception : " + e);
        } finally {
            return optionalVideo;
        }
    }
}
