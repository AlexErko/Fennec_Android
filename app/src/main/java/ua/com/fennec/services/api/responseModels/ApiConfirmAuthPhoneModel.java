package ua.com.fennec.services.api.responseModels;

import org.json.JSONException;
import org.json.JSONObject;

public class ApiConfirmAuthPhoneModel {
    public ApiAnswerModel answer;
    public String token;
    public ApiConfirmAuthPhoneModel(String jsonString) throws JSONException {
        this.answer = new ApiAnswerModel(jsonString);
        if (jsonString.contains("token")) {
            JSONObject jsonObject = new JSONObject(jsonString);
            this.token = jsonObject.getJSONObject("result").getString("token");
        }
    }
}
