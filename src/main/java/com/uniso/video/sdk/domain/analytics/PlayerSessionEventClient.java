package com.uniso.video.sdk.domain.analytics;

import com.uniso.video.sdk.domain.exception.ResponseException;


public interface PlayerSessionEventClient {
    Iterable<PlayerSessionEvent> list(String sessionId) throws ResponseException;
}
