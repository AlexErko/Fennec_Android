package ua.com.fennec.services.api.responseModels;

import org.json.JSONException;
import org.json.JSONObject;

public class ApiConfirmAuthPhoneResponse {
    public ApiAnswerResponse answer;
    public String token;
    public ApiConfirmAuthPhoneResponse(String jsonString) throws JSONException {
        this.answer = new ApiAnswerResponse(jsonString);
        if (jsonString.contains("token")) {
            JSONObject jsonObject = new JSONObject(jsonString);
            this.token = jsonObject.getJSONObject("result").getString("token");
        }
    }
}
