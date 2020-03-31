package com.uniso.video.sdk.infrastructure.unirest.token;

import kong.unirest.JsonNode;
import com.uniso.video.sdk.domain.exception.ResponseException;
import com.uniso.video.sdk.infrastructure.unirest.AuthRequestExecutor;
import com.uniso.video.sdk.infrastructure.unirest.request.RequestBuilder;
import com.uniso.video.sdk.infrastructure.unirest.request.RequestBuilderFactory;

import static kong.unirest.HttpMethod.POST;

public class TokenClient {
    private final RequestBuilderFactory requestBuilderFactory;
    private final AuthRequestExecutor   requestExecutor;

    public TokenClient(RequestBuilderFactory requestBuilderFactory, AuthRequestExecutor requestExecutor) {
        this.requestBuilderFactory = requestBuilderFactory;
        this.requestExecutor       = requestExecutor;
    }

    public String get() throws ResponseException {
        RequestBuilder request = requestBuilderFactory
                .create(POST, "/tokens");

        JsonNode responseBody = requestExecutor.executeJson(request);

        // TODO use serializer
        return responseBody.getObject().getString("token");
    }

    public static void main(String[] args) throws ResponseException {
        RequestBuilderFactory requestBuilderFactory = new RequestBuilderFactory("https://sandbox.api.video");
        TokenClient tokenClient = new TokenClient(requestBuilderFactory,
                new AuthRequestExecutor(requestBuilderFactory,"Xc1YmjmUzqNkTtdXQDtVyDQeB5sF1fPtoit5scz5pWw"));

        String key = tokenClient.get();
        System.out.println("KEY - " + key);
    }
}
