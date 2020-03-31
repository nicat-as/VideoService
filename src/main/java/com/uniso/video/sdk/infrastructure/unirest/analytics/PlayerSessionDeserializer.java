package com.uniso.video.sdk.infrastructure.unirest.analytics;

import org.json.JSONException;
import org.json.JSONObject;
import com.uniso.video.sdk.domain.analytics.*;
import com.uniso.video.sdk.infrastructure.unirest.serializer.JsonDeserializer;

public class PlayerSessionDeserializer implements JsonDeserializer<PlayerSession> {

    @Override
    public PlayerSession deserialize(JSONObject data) throws JSONException {
        return new PlayerSession(
                data.has("session") ? deserializeInfo(data.getJSONObject("session")) : null,
                data.has("location") ? deserializeLocation(data.getJSONObject("location")) : null,
                data.has("referrer") ? deserializeReferrer(data.getJSONObject("referrer")) : null,
                data.has("device") ? deserializeDevice(data.getJSONObject("device")) : null,
                data.has("os") ? deserializeOperatingSystem(data.getJSONObject("os")) : null,
                data.has("client") ? deserializeClient(data.getJSONObject("client")) : null
        );
    }

    private Info deserializeInfo(JSONObject object) {
        return new Info(
                object.getString("sessionId"),
                deserializeDateTime(object.getString("endedAt")),
                deserializeDateTime(object.getString("loadedAt")),
                object.has("metadata") ? convertKeyValueJsonArrayToMap(object.getJSONArray("metadata")) : null
        );
    }

    private Location deserializeLocation(JSONObject object) {
        return new Location(
                object.getString("country"),
                object.getString("city")
        );
    }

    private Referrer deserializeReferrer(JSONObject object) {
        return new Referrer(
                object.getString("medium"),
                object.getString("searchTerm"),
                object.getString("source"),
                object.getString("url")
        );
    }

    private Device deserializeDevice(JSONObject object) {
        return new Device(
                object.getString("model"),
                object.getString("type"),
                object.getString("vendor")
        );
    }

    private OperatingSystem deserializeOperatingSystem(JSONObject object) {
        return new OperatingSystem(
                object.getString("name"),
                object.getString("shortname"),
                object.getString("version")
        );
    }

    private Client deserializeClient(JSONObject object) {
        return new Client(
                object.getString("type"),
                object.getString("name"),
                object.getString("version")
        );
    }
}
