package com.uniso.video.sdk.domain.analytics;

import com.uniso.video.sdk.domain.QueryParams;
import com.uniso.video.sdk.domain.exception.ResponseException;


public interface LiveStreamSessionClient {
    Iterable<PlayerSession> list(String liveStreamId) throws ResponseException, IllegalArgumentException;

    Iterable<PlayerSession> list(String liveStreamId, String period) throws ResponseException, IllegalArgumentException;

    Iterable<PlayerSession> list(String liveStreamId, String period, QueryParams queryParams) throws Exception;
}
