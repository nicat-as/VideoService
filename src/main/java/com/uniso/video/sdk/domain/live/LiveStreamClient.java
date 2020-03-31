package com.uniso.video.sdk.domain.live;


import com.uniso.video.sdk.domain.QueryParams;
import com.uniso.video.sdk.domain.exception.ResponseException;

import java.io.File;
import java.io.IOException;


public interface LiveStreamClient {
    LiveStream get(String liveStreamId) throws ResponseException;

    LiveStream create(LiveStreamInput liveStreamInput) throws ResponseException;

    LiveStream uploadThumbnail(String liveStreamId, File file) throws ResponseException, IOException;

    LiveStream update(LiveStream liveStream) throws ResponseException;

    void delete(String liveStreamId) throws ResponseException;

    Iterable<LiveStream> list() throws ResponseException, IllegalArgumentException;

    Iterable<LiveStream> list(QueryParams queryParams) throws ResponseException, IllegalArgumentException;
}
