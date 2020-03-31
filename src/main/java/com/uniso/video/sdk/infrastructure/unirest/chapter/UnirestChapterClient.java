package com.uniso.video.sdk.infrastructure.unirest.chapter;

import kong.unirest.HttpMethod;
import kong.unirest.JsonNode;
import com.uniso.video.sdk.domain.chapter.Chapter;
import com.uniso.video.sdk.domain.chapter.ChapterClient;
import com.uniso.video.sdk.domain.exception.ResponseException;
import com.uniso.video.sdk.domain.pagination.PageQuery;
import com.uniso.video.sdk.infrastructure.pagination.IteratorIterable;
import com.uniso.video.sdk.infrastructure.pagination.PageIterator;
import com.uniso.video.sdk.infrastructure.unirest.RequestExecutor;
import com.uniso.video.sdk.infrastructure.unirest.pagination.UriPageLoader;
import com.uniso.video.sdk.infrastructure.unirest.request.RequestBuilder;
import com.uniso.video.sdk.infrastructure.unirest.request.RequestBuilderFactory;
import com.uniso.video.sdk.infrastructure.unirest.serializer.JsonDeserializer;

import java.io.File;
import java.io.IOException;

import static kong.unirest.HttpMethod.*;

public class UnirestChapterClient implements ChapterClient {
    private final RequestBuilderFactory requestBuilderFactory;
    private final JsonDeserializer<Chapter> deserializer;
    private final RequestExecutor requestExecutor;
    private final String videoId;

    public UnirestChapterClient(RequestBuilderFactory requestBuilderFactory, JsonDeserializer<Chapter> deserializer, RequestExecutor requestExecutor, String videoId) {
        this.requestBuilderFactory = requestBuilderFactory;
        this.deserializer = deserializer;
        this.requestExecutor = requestExecutor;
        this.videoId = videoId;
    }

    @Override
    public Chapter get(String language) throws ResponseException {
        JsonNode response = execute(
                request(GET, "/videos/" + videoId + "/chapters/" + language)
        );

        return deserializer.deserialize(response.getObject());
    }

    @Override
    public Iterable<Chapter> list() throws ResponseException {
        return new IteratorIterable<>(new PageIterator<>(new UriPageLoader<>(
                request(GET, "/videos/" + videoId + "/chapters"),
                requestExecutor,
                deserializer
        ), new PageQuery()));
    }

    @Override
    public Chapter upload(String language, File file) throws ResponseException, IOException {
        JsonNode response = execute(
                request(POST, "/videos/" + videoId + "/chapters/" + language)
                        .withFile(file)
        );

        return deserializer.deserialize(response.getObject());
    }

    @Override
    public void delete(String language) throws ResponseException {
        execute(
                request(DELETE, "/videos/" + videoId + "/chapters/" + language)
        );
    }

    private RequestBuilder request(HttpMethod method, String relativePath) {
        return requestBuilderFactory.create(method, relativePath);
    }

    private JsonNode execute(RequestBuilder request) throws ResponseException {
        return requestExecutor.executeJson(request);
    }
}
