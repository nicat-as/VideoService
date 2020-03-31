package com.uniso.video.sdk.infrastructure.unirest.account;

import kong.unirest.JsonNode;
import com.uniso.video.sdk.domain.account.Account;
import com.uniso.video.sdk.domain.exception.ResponseException;
import com.uniso.video.sdk.infrastructure.unirest.RequestExecutor;
import com.uniso.video.sdk.infrastructure.unirest.request.RequestBuilderFactory;
import com.uniso.video.sdk.infrastructure.unirest.serializer.JsonDeserializer;

import static kong.unirest.HttpMethod.GET;

public class AccountClient implements com.uniso.video.sdk.domain.account.AccountClient {
    private final RequestBuilderFactory     requestBuilderFactory;
    private final JsonDeserializer<Account> deserializer;
    private final RequestExecutor           requestExecutor;

    public AccountClient(RequestBuilderFactory requestBuilderFactory, JsonDeserializer<Account> deserializer, RequestExecutor requestExecutor) {
        this.requestBuilderFactory = requestBuilderFactory;
        this.deserializer          = deserializer;
        this.requestExecutor       = requestExecutor;
    }

    public Account get() throws ResponseException {
        JsonNode responseBody = requestExecutor.executeJson(
                requestBuilderFactory.create(GET, "/account")
        );

        return deserializer.deserialize(responseBody.getObject());
    }

}