package ua.com.fennec.services.api.bodyModels;

import org.json.JSONException;
import org.json.JSONObject;

public class ConfirmAuthPhoneBody {
    public String phone;
    public String code;

    public ConfirmAuthPhoneBody(String phone, String code) {
        this.code = code;
        this.phone = phone.replace("+", "").replace("(", "").replace(")", "").replace(" ", "").replace("-", "");
    }

    public JSONObject getBody() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("user_phone",phone);
            jsonObject.put("confirm_code",code);

        } catch (JSONException exception) {
            exception.printStackTrace();
        }
        return jsonObject;
    }
}
