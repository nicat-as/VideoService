package com.uniso.video.sdk.infrastructure.unirest.video;

import org.json.JSONException;
import org.json.JSONObject;
import com.uniso.video.sdk.domain.video.ReceivedBytes;
import com.uniso.video.sdk.infrastructure.unirest.serializer.JsonDeserializer;

public class ReceivedBytesSerializer implements JsonDeserializer<ReceivedBytes> {

    public ReceivedBytes deserialize(JSONObject data) throws JSONException {
        return new ReceivedBytes(
                data.getInt("to"),
                data.getInt("from"),
                data.getInt("total")
        );
    }
}
