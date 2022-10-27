package ua.com.fennec.services.api.responseModels;

import org.json.JSONException;
import org.json.JSONObject;

import ua.com.fennec.models.Profile;

public class ApiGetProfileModel {
    public ApiAnswerModel answer;
    public Profile profile;
    public ApiGetProfileModel(String jsonString) throws JSONException {
        this.answer = new ApiAnswerModel(jsonString);
        this.profile = new Profile(jsonString);
    }
}
