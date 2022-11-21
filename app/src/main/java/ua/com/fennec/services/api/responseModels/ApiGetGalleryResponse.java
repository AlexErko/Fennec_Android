package ua.com.fennec.services.api.responseModels;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ApiGetGalleryResponse {
    public ApiAnswerResponse answer;
    public ArrayList<String> photos = new ArrayList<>();
    public ApiGetGalleryResponse(String jsonString) throws JSONException {
        this.answer = new ApiAnswerResponse(jsonString);
        JSONArray array = new JSONObject(jsonString).getJSONArray("result");

        for (int i = 0; i < array.length(); i++) {
            photos.add(array.getString(i));
        }
    }
}
