package com.uniso.video.sdk.infrastructure.unirest.caption;

import org.json.JSONException;
import org.json.JSONObject;
import com.uniso.video.sdk.domain.caption.Caption;
import com.uniso.video.sdk.infrastructure.unirest.serializer.JsonDeserializer;

public class CaptionDeserializer implements JsonDeserializer<Caption> {

    @Override
    public Caption deserialize(JSONObject data) throws JSONException {
        Caption caption = new Caption(
                data.getString("srclang"),
                data.getString("uri"),
                data.getString("src")
        );

        if (data.has("default")) {
            caption.isDefault = data.getBoolean("default");
        }

        return caption;
    }

    public JSONObject serialize(Caption object) throws JSONException {
        JSONObject data = new JSONObject();

        data.put("default", object.isDefault);

        return data;
    }

}
