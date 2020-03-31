package com.uniso.video.sdk.infrastructure.unirest.live;

import org.json.JSONException;
import org.json.JSONObject;
import com.uniso.video.sdk.domain.live.LiveStreamInput;
import com.uniso.video.sdk.infrastructure.unirest.serializer.JsonSerializer;

public class LiveStreamInputSerializer implements JsonSerializer<LiveStreamInput> {
    @Override
    public JSONObject serialize(LiveStreamInput object) throws JSONException {
        JSONObject data = new JSONObject();
        if (object.name != null) {
            data.put("name", object.name);
        }
        data.put("record", object.record);

        if (object.playerId != null) {
            data.put("playerId", object.playerId);
        }

        return data;
    }
}
