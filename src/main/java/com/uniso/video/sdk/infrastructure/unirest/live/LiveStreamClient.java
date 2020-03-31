package com.uniso.video.sdk.infrastructure.unirest.live;

import kong.unirest.JsonNode;
import com.uniso.video.sdk.domain.QueryParams;
import com.uniso.video.sdk.domain.exception.ResponseException;
import com.uniso.video.sdk.domain.live.LiveStream;
import com.uniso.video.sdk.domain.live.LiveStreamInput;
import com.uniso.video.sdk.domain.pagination.PageQuery;
import com.uniso.video.sdk.infrastructure.pagination.IteratorIterable;
import com.uniso.video.sdk.infrastructure.pagination.PageIterator;
import com.uniso.video.sdk.infrastructure.unirest.RequestExecutor;
import com.uniso.video.sdk.infrastructure.unirest.pagination.UriPageLoader;
import com.uniso.video.sdk.infrastructure.unirest.request.RequestBuilder;
import com.uniso.video.sdk.infrastructure.unirest.request.RequestBuilderFactory;
import com.uniso.video.sdk.infrastructure.unirest.serializer.JsonDeserializer;
import com.uniso.video.sdk.infrastructure.unirest.serializer.JsonSerializer;

import java.io.File;
import java.io.IOException;

import static kong.unirest.HttpMethod.*;

public class LiveStreamClient implements com.uniso.video.sdk.domain.live.LiveStreamClient {

    private final RequestBuilderFactory           requestBuilderFactory;
    private final JsonSerializer<LiveStreamInput> serializer;
    private final JsonDeserializer<LiveStream>    deserializer;
    private final RequestExecutor                 requestExecutor;

    public LiveStreamClient(RequestBuilderFactory requestBuilderFactory, JsonSerializer<LiveStreamInput> serializer, JsonDeserializer<LiveStream> deserializer, RequestExecutor requestExecutor) {
        this.requestBuilderFactory = requestBuilderFactory;
        this.serializer            = serializer;
        this.deserializer          = deserializer;
        this.requestExecutor       = requestExecutor;
    }

    public LiveStream get(String liveStreamId) throws ResponseException {
        RequestBuilder request = requestBuilderFactory
                .create(GET, "/live-streams/" + liveStreamId);

        JsonNode responseBody = requestExecutor.executeJson(request);

        return deserializer.deserialize(responseBody.getObject());
    }

    public LiveStream create(LiveStreamInput liveStreamInput) throws ResponseException {
        RequestBuilder request = requestBuilderFactory
                .create(POST, "/live-streams")
                .withJson(serializer.serialize(liveStreamInput));

        JsonNode responseBody = requestExecutor.executeJson(request);

        return deserializer.deserialize(responseBody.getObject());
    }

    public LiveStream uploadThumbnail(String liveStreamId, File file) throws ResponseException, IOException {
        RequestBuilder request = requestBuilderFactory
                .create(POST, "/live-streams/" + liveStreamId + "/thumbnail")
                .withFile(file);

        JsonNode responseBody = requestExecutor.executeJson(request);

        return deserializer.deserialize(responseBody.getObject());
    }

    public LiveStream update(LiveStream liveStream) throws ResponseException {
        RequestBuilder request = requestBuilderFactory
                .create(PATCH, "/live-streams/" + liveStream.liveStreamId)
                .withJson(serializer.serialize(liveStream));

        JsonNode responseBody = requestExecutor.executeJson(request);

        return deserializer.deserialize(responseBody.getObject());
    }

    public void delete(String liveStreamId) throws ResponseException {
        RequestBuilder request = requestBuilderFactory
                .create(DELETE, "/live-streams/" + liveStreamId);

        requestExecutor.executeJson(request);
    }

    public Iterable<LiveStream> list() throws ResponseException, IllegalArgumentException {
        return list(new QueryParams());
    }

    public Iterable<LiveStream> list(QueryParams queryParams) throws ResponseException, IllegalArgumentException {
        return new IteratorIterable<>(new PageIterator<>(new UriPageLoader<>(
                requestBuilderFactory.create(GET, "/live-streams"),
                requestExecutor,
                deserializer
        ), new PageQuery()));
    }

}

