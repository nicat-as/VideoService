package com.uniso.video.sdk.infrastructure.unirest.chapter;

import org.json.JSONException;
import org.json.JSONObject;
import com.uniso.video.sdk.domain.chapter.Chapter;
import com.uniso.video.sdk.infrastructure.unirest.serializer.JsonDeserializer;

public class ChapterDeserializer implements JsonDeserializer<Chapter> {

    @Override
    public Chapter deserialize(JSONObject data) throws JSONException {
        return new Chapter(
                data.getString("language"),
                data.getString("uri"),
                data.getString("src")
        );
    }
}
