package com.uniso.video.sdk.domain.analytics;

import com.uniso.video.sdk.domain.QueryParams;
import com.uniso.video.sdk.domain.exception.ResponseException;


public interface VideoSessionClient {
    Iterable<PlayerSession> list(String videoId) throws ResponseException, IllegalArgumentException;

    Iterable<PlayerSession> list(String videoId, String period) throws ResponseException, IllegalArgumentException;

    Iterable<PlayerSession> list(String videoId, String period, QueryParams queryParams) throws ResponseException, IllegalArgumentException;
}
