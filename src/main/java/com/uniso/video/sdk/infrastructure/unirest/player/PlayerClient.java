package com.uniso.video.sdk.infrastructure.unirest.player;

import kong.unirest.JsonNode;
import com.uniso.video.sdk.domain.QueryParams;
import com.uniso.video.sdk.domain.exception.ResponseException;
import com.uniso.video.sdk.domain.pagination.PageQuery;
import com.uniso.video.sdk.domain.player.Player;
import com.uniso.video.sdk.domain.player.PlayerInput;
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

public class PlayerClient implements com.uniso.video.sdk.domain.player.PlayerClient {
    private final RequestBuilderFactory       requestBuilderFactory;
    private final JsonSerializer<PlayerInput> serializer;
    private final JsonDeserializer<Player>    deserializer;
    private final RequestExecutor             requestExecutor;

    public PlayerClient(RequestBuilderFactory requestBuilderFactory, JsonSerializer<PlayerInput> serializer, JsonDeserializer<Player> deserializer, RequestExecutor requestExecutor) {
        this.requestBuilderFactory = requestBuilderFactory;
        this.serializer            = serializer;
        this.deserializer          = deserializer;
        this.requestExecutor       = requestExecutor;
    }

    public Player get(String playerId) throws ResponseException {
        RequestBuilder request = requestBuilderFactory
                .create(GET, "/players/" + playerId);

        JsonNode responseBody = requestExecutor.executeJson(request);

        return deserializer.deserialize(responseBody.getObject());
    }

    public Player create(PlayerInput player) throws ResponseException {
        RequestBuilder request = requestBuilderFactory
                .create(POST, "/players")
                .withJson(serializer.serialize(player));

        JsonNode responseBody = requestExecutor.executeJson(request);

        return deserializer.deserialize(responseBody.getObject());
    }

    public Player update(Player player) throws ResponseException {
        RequestBuilder request = requestBuilderFactory
                .create(PATCH, "/players/" + player.playerId)
                .withJson(serializer.serialize(player));

        JsonNode responseBody = requestExecutor.executeJson(request);

        return deserializer.deserialize(responseBody.getObject());
    }

    public Player uploadLogo(String playerId, File file, String link) throws ResponseException, IOException {
        RequestBuilder request = requestBuilderFactory
                .create(POST, "/players/" + playerId + "/logo")
                .withFile(file, link);

        JsonNode responseBody = requestExecutor.executeJson(request);

        return deserializer.deserialize(responseBody.getObject());
    }

    public void deleteLogo(String playerId) throws ResponseException {
        RequestBuilder request = requestBuilderFactory
                .create(DELETE, "/players/" + playerId + "/logo");

        requestExecutor.executeJson(request);
    }

    public void delete(String playerId) throws ResponseException {
        RequestBuilder request = requestBuilderFactory
                .create(DELETE, "/players/" + playerId);

        requestExecutor.executeJson(request);
    }

    public Iterable<Player> list() throws ResponseException, IllegalArgumentException {
        return list(new QueryParams());
    }

    public Iterable<Player> list(QueryParams queryParams) throws ResponseException, IllegalArgumentException {
        return new IteratorIterable<>(new PageIterator<>(new UriPageLoader<>(
                requestBuilderFactory.create(GET, "/players"),
                requestExecutor,
                deserializer
        ), new PageQuery()));
    }

}
