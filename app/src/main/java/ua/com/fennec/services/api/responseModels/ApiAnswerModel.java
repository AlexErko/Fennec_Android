package ua.com.fennec.services.api.responseModels;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import ua.com.fennec.Constants;

public class ApiAnswerModel {
    public Integer status;
    public String message;

    public ApiAnswerModel(String jsonString) throws JSONException {
            JSONObject json = new JSONObject(jsonString);
            this.status = json.getInt("status");
            this.message = json.getString("message");
    }
}
