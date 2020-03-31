package com.uniso.video.sdk;

import com.uniso.video.sdk.domain.account.AccountClient;
import com.uniso.video.sdk.domain.analytics.LiveStreamSessionClient;
import com.uniso.video.sdk.domain.analytics.PlayerSessionEventClient;
import com.uniso.video.sdk.domain.analytics.VideoSessionClient;
import com.uniso.video.sdk.domain.caption.CaptionClient;
import com.uniso.video.sdk.domain.chapter.ChapterClient;
import com.uniso.video.sdk.domain.live.LiveStreamClient;
import com.uniso.video.sdk.domain.player.PlayerClient;
import com.uniso.video.sdk.domain.video.VideoClient;
import com.uniso.video.sdk.infrastructure.unirest.chapter.UnirestChapterClientFactory;

public class Client {
    public final  AccountClient               account;
    public final  CaptionClient               captions;
    public final  LiveStreamClient            liveStreams;
    public final  LiveStreamSessionClient     liveStreamAnalytics;
    public final  PlayerClient                players;
    public final  PlayerSessionEventClient    playerSessionEvents;
    public final  VideoClient                 videos;
    public final  VideoSessionClient          videoAnalytics;
    private final UnirestChapterClientFactory chapterClientFactory;

    public Client(
            AccountClient account,
            CaptionClient captions,
            LiveStreamClient liveStreams,
            LiveStreamSessionClient liveStreamAnalytics,
            PlayerClient players,
            PlayerSessionEventClient playerSessionEvents,
            VideoClient videos,
            VideoSessionClient videoAnalytics,
            UnirestChapterClientFactory chapterClientFactory) {
        this.account              = account;
        this.captions             = captions;
        this.liveStreams          = liveStreams;
        this.liveStreamAnalytics  = liveStreamAnalytics;
        this.players              = players;
        this.playerSessionEvents  = playerSessionEvents;
        this.videos               = videos;
        this.videoAnalytics       = videoAnalytics;
        this.chapterClientFactory = chapterClientFactory;
    }

    public ChapterClient chapters(String videoId) {
        return chapterClientFactory.create(videoId);
    }
}
