package com.uniso.video.sdk.infrastructure.unirest.analytics;

import com.uniso.video.sdk.domain.QueryParams;
import com.uniso.video.sdk.domain.analytics.PlayerSession;
import com.uniso.video.sdk.domain.exception.ResponseException;
import com.uniso.video.sdk.domain.pagination.PageQuery;
import com.uniso.video.sdk.infrastructure.pagination.IteratorIterable;
import com.uniso.video.sdk.infrastructure.pagination.PageIterator;
import com.uniso.video.sdk.infrastructure.unirest.RequestExecutor;
import com.uniso.video.sdk.infrastructure.unirest.pagination.UriPageLoader;
import com.uniso.video.sdk.infrastructure.unirest.request.RequestBuilderFactory;
import com.uniso.video.sdk.infrastructure.unirest.serializer.JsonDeserializer;

import static kong.unirest.HttpMethod.GET;

public class VideoSessionClient implements com.uniso.video.sdk.domain.analytics.VideoSessionClient {
    private final RequestBuilderFactory           requestBuilderFactory;
    private final JsonDeserializer<PlayerSession> deserializer;
    private final RequestExecutor                 requestExecutor;

    public VideoSessionClient(RequestBuilderFactory requestBuilderFactory, JsonDeserializer<PlayerSession> deserializer, RequestExecutor requestExecutor) {
        this.requestBuilderFactory = requestBuilderFactory;
        this.deserializer          = deserializer;
        this.requestExecutor       = requestExecutor;
    }

    public Iterable<PlayerSession> list(String videoId) throws ResponseException, IllegalArgumentException {
        return list(videoId, null, new QueryParams());
    }

    public Iterable<PlayerSession> list(String videoId, String period) throws ResponseException, IllegalArgumentException {
        return list(videoId, period, new QueryParams());
    }

    public Iterable<PlayerSession> list(String videoId, String period, QueryParams queryParams) throws ResponseException, IllegalArgumentException {
        queryParams.period = period;

        return new IteratorIterable<>(new PageIterator<>(new UriPageLoader<>(
                requestBuilderFactory.create(GET, "/analytics/videos/" + videoId)
                        .withQueryParams(queryParams.toMap()),
                requestExecutor,
                deserializer
        ), new PageQuery()));
    }
}
