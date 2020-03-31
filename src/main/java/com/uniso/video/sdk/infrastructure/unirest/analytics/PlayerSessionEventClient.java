package com.uniso.video.sdk.infrastructure.unirest.analytics;

import com.uniso.video.sdk.domain.analytics.PlayerSessionEvent;
import com.uniso.video.sdk.domain.exception.ResponseException;
import com.uniso.video.sdk.domain.pagination.PageQuery;
import com.uniso.video.sdk.infrastructure.pagination.IteratorIterable;
import com.uniso.video.sdk.infrastructure.pagination.PageIterator;
import com.uniso.video.sdk.infrastructure.unirest.RequestExecutor;
import com.uniso.video.sdk.infrastructure.unirest.pagination.UriPageLoader;
import com.uniso.video.sdk.infrastructure.unirest.request.RequestBuilderFactory;
import com.uniso.video.sdk.infrastructure.unirest.serializer.JsonDeserializer;

import static kong.unirest.HttpMethod.GET;

public class PlayerSessionEventClient implements com.uniso.video.sdk.domain.analytics.PlayerSessionEventClient {
    private final RequestBuilderFactory                requestBuilderFactory;
    private final JsonDeserializer<PlayerSessionEvent> deserializer;
    private final RequestExecutor                      requestExecutor;

    public PlayerSessionEventClient(RequestBuilderFactory requestBuilderFactory, JsonDeserializer<PlayerSessionEvent> deserializer, RequestExecutor requestExecutor) {
        this.requestBuilderFactory = requestBuilderFactory;
        this.deserializer          = deserializer;
        this.requestExecutor       = requestExecutor;
    }

    public Iterable<PlayerSessionEvent> list(String sessionId) throws ResponseException, IllegalArgumentException {
        return new IteratorIterable<>(new PageIterator<>(new UriPageLoader<>(
                requestBuilderFactory.create(GET, "/analytics/sessions/" + sessionId + "/events"),
                requestExecutor,
                deserializer
        ), new PageQuery()));
    }
}
