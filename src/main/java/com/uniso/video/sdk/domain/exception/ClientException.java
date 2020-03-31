package com.uniso.video.sdk.domain.exception;

import kong.unirest.JsonNode;

public class ClientException extends ResponseException {

    public ClientException(String message, JsonNode responseBody, int status) {
        super(message, responseBody, status);
    }
}
