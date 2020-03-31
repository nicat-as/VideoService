package com.uniso.video.sdk.domain.video;

import com.uniso.video.sdk.domain.QueryParams;
import com.uniso.video.sdk.domain.exception.ResponseException;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

public interface VideoClient {

    Iterable<Video> list() throws ResponseException;

    Iterable<Video> list(QueryParams queryParams) throws ResponseException, URISyntaxException;

    Video get(String videoId) throws ResponseException;

    Status getStatus(String videoId) throws ResponseException;

    Video create(VideoInput videoInput) throws ResponseException;

    Video upload(File file) throws ResponseException;

    Video upload(File file, VideoInput videoInput) throws ResponseException;

    Video upload(File file, UploadProgressListener m) throws ResponseException;

    Video upload(File file, VideoInput video, UploadProgressListener m) throws ResponseException;

    Video uploadThumbnail(String videoId, File file) throws ResponseException, IOException;

    Video updateThumbnail(String videoId, String timecode) throws ResponseException;

    Video update(Video video) throws ResponseException;

    void delete(String videoId) throws ResponseException;
}
