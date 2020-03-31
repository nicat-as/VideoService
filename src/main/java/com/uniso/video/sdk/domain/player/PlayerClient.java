package com.uniso.video.sdk.domain.player;

import com.uniso.video.sdk.domain.QueryParams;
import com.uniso.video.sdk.domain.exception.ResponseException;

import java.io.File;
import java.io.IOException;


public interface PlayerClient {

    Player get(String playerId) throws ResponseException;

    Player create(PlayerInput player) throws ResponseException;

    Player update(Player player) throws ResponseException;

    Player uploadLogo(String playerId, File file, String link) throws ResponseException, IOException;

    void deleteLogo(String playerId) throws ResponseException;

    void delete(String playerId) throws ResponseException;

    Iterable<Player> list() throws ResponseException;

    Iterable<Player> list(QueryParams queryParams) throws ResponseException, IllegalArgumentException;

}
