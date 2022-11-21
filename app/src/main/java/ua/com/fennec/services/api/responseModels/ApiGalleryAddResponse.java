package ua.com.fennec.services.api.responseModels;

import org.json.JSONException;
import org.json.JSONObject;

public class ApiGalleryAddResponse {
    public ApiAnswerResponse answer;
    public String filepath;
    public ApiGalleryAddResponse(String jsonString) throws JSONException {
        this.answer = new ApiAnswerResponse(jsonString);
        if (jsonString.contains("filename")) {
            JSONObject jsonObject = new JSONObject(jsonString);
            this.filepath = jsonObject.getJSONObject("result").getString("filename");
        }
    }
}
