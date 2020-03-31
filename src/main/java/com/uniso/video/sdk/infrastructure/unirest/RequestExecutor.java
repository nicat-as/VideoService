package com.uniso.video.sdk.infrastructure.unirest;

import kong.unirest.JsonNode;
import com.uniso.video.sdk.domain.exception.ResponseException;
import com.uniso.video.sdk.infrastructure.unirest.request.RequestBuilder;

public interface RequestExecutor {
    JsonNode executeJson(RequestBuilder requestBuilder) throws ResponseException;
}
