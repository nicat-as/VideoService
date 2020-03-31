package com.uniso.video.sdk.infrastructure.unirest.chapter;

import com.uniso.video.sdk.domain.chapter.Chapter;
import com.uniso.video.sdk.domain.chapter.ChapterClient;
import com.uniso.video.sdk.infrastructure.unirest.RequestExecutor;
import com.uniso.video.sdk.infrastructure.unirest.request.RequestBuilderFactory;
import com.uniso.video.sdk.infrastructure.unirest.serializer.JsonDeserializer;

public class UnirestChapterClientFactory {
    private final RequestBuilderFactory requestBuilderFactory;
    private final JsonDeserializer<Chapter> deserializer;
    private final RequestExecutor requestExecutor;

    public UnirestChapterClientFactory(RequestBuilderFactory requestBuilderFactory, JsonDeserializer<Chapter> deserializer, RequestExecutor requestExecutor) {
        this.requestBuilderFactory = requestBuilderFactory;
        this.deserializer = deserializer;
        this.requestExecutor = requestExecutor;
    }

    public ChapterClient create(String videoId) {
        return new UnirestChapterClient(requestBuilderFactory, deserializer, requestExecutor, videoId);
    }

}
