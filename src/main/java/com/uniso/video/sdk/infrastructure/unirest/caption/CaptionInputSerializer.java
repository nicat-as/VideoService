package com.uniso.video.sdk.infrastructure.unirest.caption;

import org.json.JSONException;
import org.json.JSONObject;
import com.uniso.video.sdk.domain.caption.CaptionInput;
import com.uniso.video.sdk.infrastructure.unirest.serializer.JsonSerializer;

public class CaptionInputSerializer implements JsonSerializer<CaptionInput> {

    public JSONObject serialize(CaptionInput object) throws JSONException {
        JSONObject data = new JSONObject();

        data.put("default", object.isDefault);

        return data;
    }

}
