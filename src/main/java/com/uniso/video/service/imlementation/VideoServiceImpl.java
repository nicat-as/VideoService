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
import java.io.IOException;
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
            logger.info("Getting status .. ");
            Status status1 = client.videos.getStatus(status);
            logger.info("Status : " + status);
            optionalStatus = Optional.of(status1);
        } catch (ResponseException e) {
            e.printStackTrace();
            logger.error("Not getting status : " + e);
        } finally {
            return optionalStatus;
        }

    }

    @Override
    public List<Video> getVideos() {
        List<Video> list = new ArrayList<>();
        try {
            logger.info("Getting video List .. ");
            Iterable<Video> videos = client.videos.list();
            for (Video v : videos) {
                list.add(v);
            }
            logger.info("Video list got");

        } catch (ResponseException e) {
            logger.error("Couldn't get videos", e);
            e.printStackTrace();
        } finally {
            return list;
        }
    }

    @Override
    public List<Video> getVideosByQuery(QueryParams params) {
        List<Video> videos = new ArrayList<>();
        logger.info("Getting video list by query ...  ");
        try {
            Iterable<Video> iterable = client.videos.list(params);
            iterable.forEach(i -> videos.add(i));
            logger.info("Video list got!");
        } catch (ResponseException | URISyntaxException e) {
            logger.error("Couldn't get video by query ", e);
            e.printStackTrace();
        } finally {
            return videos;
        }
    }

    @Override
    public Optional<Video> showVideo(String videoId) {
        Optional<Video> optionalVideo = Optional.empty();
        try {
            logger.info("Getting video : " + videoId);
            Video video = client.videos.get(videoId);
            logger.info("Video : " + video);
            optionalVideo = Optional.of(video);
        } catch (ResponseException e) {
            logger.error("Couldn't get video by videoId ", e);
            e.printStackTrace();
        } finally {
            return optionalVideo;
        }
    }

    @Override
    public Optional<Video> uploadThumbnail(String videoId, MultipartFile image) {
        Optional<Video> optionalVideo = Optional.empty();
        try {
            logger.info("Storing image file.. ");
            String path = storageService.store(image);
            Video video = client.videos.uploadThumbnail(videoId, new File(path));
            logger.info("Uploaded thumbnail " + video.videoId + " : " + video.assets.get("thumbnail"));
            storageService.deleteFile(path);
            logger.info("Deleting path : " + path);
            optionalVideo = Optional.of(video);

        } catch (ResponseException | IOException e) {
            e.printStackTrace();
        } finally {
            return optionalVideo;
        }
    }

    @Override
    public Optional<Video> pickThumbnail(String videoId, String pattern) {
        Optional<Video> optionalVideo = Optional.empty();
        try {
            Video video = client.videos.updateThumbnail(videoId, pattern);
            optionalVideo = Optional.of(video);
        } catch (ResponseException e) {
            e.printStackTrace();
        } finally {
            return optionalVideo;
        }

    }
}
