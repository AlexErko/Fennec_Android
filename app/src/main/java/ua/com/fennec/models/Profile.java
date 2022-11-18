package ua.com.fennec.models;

import org.json.JSONException;
import org.json.JSONObject;

import ua.com.fennec.services.api.responseModels.ApiAnswerModel;
import ua.com.fennec.services.string.StringService;

public class Profile {
    public String company_name;
    public String company_logo;
    public String user_logo;
    public String user_name;
    public String user_phone;
    public String user_email;

    public Profile(String jsonString) throws JSONException {
        JSONObject jsonObject = new JSONObject(jsonString);
        this.user_name = jsonObject.getJSONObject("result").getString("user_name");
        this.user_phone = jsonObject.getJSONObject("result").getString("user_phone");
        this.company_logo = jsonObject.getJSONObject("result").getString("company_logo");
        this.user_logo = jsonObject.getJSONObject("result").getString("user_logo");
        this.user_email = jsonObject.getJSONObject("result").getString("user_email");
        this.company_name = jsonObject.getJSONObject("result").getString("company_name");
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        JSONObject resultObject = new JSONObject();
        resultObject.put("user_name", user_name);
        resultObject.put("user_phone", user_phone);
        resultObject.put("company_logo", company_logo);
        resultObject.put("user_logo", user_logo);
        resultObject.put("user_email", user_email);
        resultObject.put("company_name", company_name);

        jsonObject.put("result",resultObject);
        return jsonObject;
    }

    @Override
    public String toString() {
        return "Profile{" +
                "company_name='" + company_name + '\'' +
                ", company_logo='" + company_logo + '\'' +
                ", user_logo='" + user_logo + '\'' +
                ", user_name='" + user_name + '\'' +
                ", user_phone='" + user_phone + '\'' +
                ", user_email='" + user_email + '\'' +
                '}';
    }
}
