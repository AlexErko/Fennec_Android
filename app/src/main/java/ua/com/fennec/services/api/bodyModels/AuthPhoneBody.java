package ua.com.fennec.services.api.bodyModels;

import org.json.JSONException;
import org.json.JSONObject;

public class AuthPhoneBody {
    String phone;

    public AuthPhoneBody(String phone) {
        this.phone = phone.replace("+", "").replace("(", "").replace(")", "").replace(" ", "").replace("-", "");
    }

    public JSONObject getBody() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("user_phone",phone);
        } catch (JSONException exception) {
            exception.printStackTrace();
        }
        return jsonObject;
    }
}
