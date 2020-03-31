package com.uniso.video.sdk;


import com.uniso.video.sdk.infrastructure.unirest.AuthRequestExecutor;
import com.uniso.video.sdk.infrastructure.unirest.account.AccountClient;
import com.uniso.video.sdk.infrastructure.unirest.account.AccountDeserializer;
import com.uniso.video.sdk.infrastructure.unirest.analytics.*;
import com.uniso.video.sdk.infrastructure.unirest.caption.CaptionClient;
import com.uniso.video.sdk.infrastructure.unirest.caption.CaptionDeserializer;
import com.uniso.video.sdk.infrastructure.unirest.caption.CaptionInputSerializer;
import com.uniso.video.sdk.infrastructure.unirest.chapter.ChapterDeserializer;
import com.uniso.video.sdk.infrastructure.unirest.chapter.UnirestChapterClientFactory;
import com.uniso.video.sdk.infrastructure.unirest.live.LiveStreamClient;
import com.uniso.video.sdk.infrastructure.unirest.live.LiveStreamDeserializer;
import com.uniso.video.sdk.infrastructure.unirest.live.LiveStreamInputSerializer;
import com.uniso.video.sdk.infrastructure.unirest.player.PlayerClient;
import com.uniso.video.sdk.infrastructure.unirest.player.PlayerDeserializer;
import com.uniso.video.sdk.infrastructure.unirest.player.PlayerInputSerializer;
import com.uniso.video.sdk.infrastructure.unirest.request.RequestBuilderFactory;
import com.uniso.video.sdk.infrastructure.unirest.video.UnirestVideoClient;
import com.uniso.video.sdk.infrastructure.unirest.video.VideoDeserializer;
import com.uniso.video.sdk.infrastructure.unirest.video.VideoInputSerializer;

public class ClientFactory {
    public Client create(String apiKey) {
        return create(apiKey, "https://ws.api.video");
    }

    public Client createSandbox(String apiKey) {
        return create(apiKey, "https://sandbox.api.video");
    }

    protected Client create(String apiKey, String baseUri) {
        RequestBuilderFactory requestBuilderFactory = new RequestBuilderFactory(baseUri);
        AuthRequestExecutor requestExecutor = new AuthRequestExecutor(requestBuilderFactory, apiKey);

        return new Client(
                new AccountClient(requestBuilderFactory, new AccountDeserializer(), requestExecutor),
                new CaptionClient(requestBuilderFactory, new CaptionInputSerializer(), new CaptionDeserializer(), requestExecutor),
                new LiveStreamClient(requestBuilderFactory, new LiveStreamInputSerializer(), new LiveStreamDeserializer(), requestExecutor),
                new LiveStreamSessionClient(requestBuilderFactory, new PlayerSessionDeserializer(), requestExecutor),
                new PlayerClient(requestBuilderFactory, new PlayerInputSerializer(), new PlayerDeserializer(), requestExecutor),
                new PlayerSessionEventClient(requestBuilderFactory, new SessionEventJsonSerializer(), requestExecutor),
                new UnirestVideoClient(requestBuilderFactory, new VideoInputSerializer(), new VideoDeserializer(), requestExecutor),
                new VideoSessionClient(requestBuilderFactory, new PlayerSessionDeserializer(), requestExecutor),
                new UnirestChapterClientFactory(requestBuilderFactory, new ChapterDeserializer(), requestExecutor)
        );
    }
}
