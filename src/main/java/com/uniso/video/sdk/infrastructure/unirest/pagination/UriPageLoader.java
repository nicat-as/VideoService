package com.uniso.video.sdk.infrastructure.unirest.pagination;

import kong.unirest.JsonNode;
import com.uniso.video.sdk.domain.exception.ResponseException;
import com.uniso.video.sdk.domain.pagination.Page;
import com.uniso.video.sdk.domain.pagination.PageQuery;
import com.uniso.video.sdk.infrastructure.pagination.PageDeserializer;
import com.uniso.video.sdk.infrastructure.pagination.PageLoader;
import com.uniso.video.sdk.infrastructure.unirest.RequestExecutor;
import com.uniso.video.sdk.infrastructure.unirest.request.RequestBuilder;
import com.uniso.video.sdk.infrastructure.unirest.serializer.JsonDeserializer;

public class UriPageLoader<T> implements PageLoader<T> {
    private final RequestBuilder      requestBuilder;
    private final RequestExecutor     requestExecutor;
    private final JsonDeserializer<T> deserializer;

    public UriPageLoader(RequestBuilder requestBuilder, RequestExecutor requestExecutor, JsonDeserializer<T> deserializer) {
        this.requestBuilder  = requestBuilder;
        this.requestExecutor = requestExecutor;
        this.deserializer    = deserializer;
    }

    @Override
    public Page<T> load(PageQuery pageQuery) throws ResponseException {
        RequestBuilder request = requestBuilder
                .withQueryParams(pageQuery.toMap());

        JsonNode responseBody = requestExecutor.executeJson(request);

        return new PageDeserializer<>(deserializer).deserialize(responseBody.getObject());
    }
}
