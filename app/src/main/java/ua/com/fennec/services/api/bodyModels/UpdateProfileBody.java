package ua.com.fennec.services.api.bodyModels;

import org.json.JSONException;
import org.json.JSONObject;

public class UpdateProfileBody {
    public String phone;
    public String company_name;
    public String name;
    public String email;


    public UpdateProfileBody(String phone, String company_name, String name, String email) {
        this.phone = phone.replace("+", "").replace("(", "").replace(")", "").replace(" ", "").replace("-", "");
        this.name = name;
        this.company_name = company_name;
        this.email = email;
    }

    public JSONObject getBody() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("user_phone",phone);
            jsonObject.put("user_email",email);
            jsonObject.put("user_name",name);
            jsonObject.put("company_name",company_name);

        } catch (JSONException exception) {
            exception.printStackTrace();
        }
        return jsonObject;
    }
}
