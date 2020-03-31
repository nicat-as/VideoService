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

public class LiveStreamSessionClient implements com.uniso.video.sdk.domain.analytics.LiveStreamSessionClient {
    private final RequestBuilderFactory           requestBuilderFactory;
    private final JsonDeserializer<PlayerSession> serializer;
    private final RequestExecutor                 requestExecutor;

    public LiveStreamSessionClient(RequestBuilderFactory requestBuilderFactory, JsonDeserializer<PlayerSession> serializer, RequestExecutor requestExecutor) {
        this.requestBuilderFactory = requestBuilderFactory;
        this.serializer            = serializer;
        this.requestExecutor       = requestExecutor;
    }

    public Iterable<PlayerSession> list(String videoId) throws ResponseException, IllegalArgumentException {
        return list(videoId, null, new QueryParams());
    }

    public Iterable<PlayerSession> list(String videoId, String period) throws ResponseException, IllegalArgumentException {
        return list(videoId, period, new QueryParams());
    }

    public Iterable<PlayerSession> list(String liveStreamId, String period, QueryParams queryParams) throws ResponseException, IllegalArgumentException {
        queryParams.period = period;

        return new IteratorIterable<>(new PageIterator<>(new UriPageLoader<>(
                requestBuilderFactory
                        .create(GET, "/analytics/live-streams/" + liveStreamId)
                        .withQueryParams(queryParams.toMap()),
                requestExecutor,
                serializer
        ), new PageQuery()));
    }
}
