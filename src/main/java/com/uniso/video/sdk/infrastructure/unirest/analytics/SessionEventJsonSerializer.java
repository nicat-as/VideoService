package com.uniso.video.sdk.infrastructure.unirest.analytics;

import org.json.JSONException;
import org.json.JSONObject;
import com.uniso.video.sdk.domain.analytics.PlayerSessionEvent;
import com.uniso.video.sdk.infrastructure.unirest.serializer.JsonDeserializer;

public class SessionEventJsonSerializer implements JsonDeserializer<PlayerSessionEvent> {
    public PlayerSessionEvent deserialize(JSONObject data) throws JSONException {
        return new PlayerSessionEvent(
                data.getString("type"),
                deserializeDateTime(data.getString("emittedAt")),
                data.has("at") ? data.getInt("at") : null,
                data.has("from") ? data.getInt("from") : null,
                data.has("to") ? data.getInt("to") : null
        );
    }
}
