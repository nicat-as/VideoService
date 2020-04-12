package com.uniso.video.service.imlementation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.uniso.video.exception.StorageException;
import com.uniso.video.sdk.Client;
import com.uniso.video.sdk.domain.QueryParams;
import com.uniso.video.sdk.domain.exception.ResponseException;
import com.uniso.video.sdk.domain.video.Status;
import com.uniso.video.sdk.domain.video.Video;
import com.uniso.video.sdk.domain.video.VideoInput;
import com.uniso.video.service.StorageService;
import com.uniso.video.service.VideoService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class VideoServiceImpl implements VideoService {
    private final Logger logger = LogManager.getLogger(VideoServiceImpl.class);

    @Autowired
    private Client client;

    @Autowired
    private StorageService storageService;


    @Override
    public Optional<Video> upload(String videoInput, MultipartFile file) {
        Optional<Video> optionalVideoResponse = Optional.empty();
        try {
            String filePath = storageService.store(file);
            logger.info(Thread.currentThread().getName() + " filePath is : " + filePath);

            ObjectMapper mapper = new ObjectMapper();
            VideoInput input = mapper.readValue(videoInput, VideoInput.class);
            Video video = client.videos.upload(new File(filePath), input);

            logger.info(Thread.currentThread().getName() + " Video information : " + video);
            optionalVideoResponse = Optional.of(video);

            logger.info(Thread.currentThread().getName() + " Deleting file from path : " + filePath);
            storageService.deleteFile(filePath);

        } catch (ResponseException e) {
            logger.error(Thread.currentThread().getName() + " Video not uploaded : " + e);
        } catch (StorageException e) {
            logger.error("Storage exception : " + e);
        } finally {
            return optionalVideoResponse;
        }
    }

    @Override
    public Optional<Status> getStatus(String status) {
        Optional<Status> optionalStatus = Optional.empty();
        try {
            Status status1 = client.videos.getStatus(status);
            optionalStatus = Optional.of(status1);
        } catch (ResponseException e) {
            e.printStackTrace();
        } finally {
            return optionalStatus;
        }

    }

    @Override
    public List<Video> getVideos() {
        List<Video> list = new ArrayList<>();
        try {
            Iterable<Video> videos = client.videos.list();
            for (Video v : videos) {
                list.add(v);
            }
        } catch (ResponseException e) {
            e.printStackTrace();
        } finally {
            return list;
        }
    }

    @Override
    public List<Video> getVideosByQuery(QueryParams params) {
        List<Video> videos = new ArrayList<>();
        try {
            Iterable<Video> iterable = client.videos.list(params);
            iterable.forEach(i -> videos.add(i));
        } catch (ResponseException | URISyntaxException e) {
            e.printStackTrace();
        } finally {
            return videos;
        }
    }


}
