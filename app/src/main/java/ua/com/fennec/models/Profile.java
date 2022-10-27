package ua.com.fennec.models;

import org.json.JSONException;
import org.json.JSONObject;

import ua.com.fennec.services.api.responseModels.ApiAnswerModel;

public class Profile {
    public String company_name;
    public String company_logo;
    public String user_name;
    public String user_phone;
    public String user_email;

    public Profile(String jsonString) throws JSONException {
            JSONObject jsonObject = new JSONObject(jsonString);
            this.user_name = jsonObject.getJSONObject("result").getString("user_name");
            this.user_phone = jsonObject.getJSONObject("result").getString("user_phone");
            this.company_logo = jsonObject.getJSONObject("result").getString("company_logo");
            this.user_email = jsonObject.getJSONObject("result").getString("user_email");
            this.company_name = jsonObject.getJSONObject("result").getString("company_name");
    }
}
