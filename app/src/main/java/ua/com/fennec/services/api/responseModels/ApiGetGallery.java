package ua.com.fennec.services.api.responseModels;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import ua.com.fennec.models.Profile;

public class ApiGetGallery {
    public ApiAnswerModel answer;
    public ArrayList<String> photos = new ArrayList<>();
    public ApiGetGallery(String jsonString) throws JSONException {
        this.answer = new ApiAnswerModel(jsonString);
        JSONArray array = new JSONObject(jsonString).getJSONArray("result");

        for (int i = 0; i < array.length(); i++) {
            photos.add(array.getString(i));
        }
    }
}
