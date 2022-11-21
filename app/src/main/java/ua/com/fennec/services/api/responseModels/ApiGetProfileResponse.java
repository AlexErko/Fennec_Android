package ua.com.fennec.services.api.responseModels;

import org.json.JSONException;

import ua.com.fennec.models.Profile;

public class ApiGetProfileResponse {
    public ApiAnswerResponse answer;
    public Profile profile;
    public ApiGetProfileResponse(String jsonString) throws JSONException {
        this.answer = new ApiAnswerResponse(jsonString);
        this.profile = new Profile(jsonString);
    }
}
