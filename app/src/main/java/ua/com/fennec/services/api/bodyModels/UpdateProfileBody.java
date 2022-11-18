package ua.com.fennec.services.api.bodyModels;

import org.json.JSONException;
import org.json.JSONObject;

public class UpdateProfileBody {
    public String phone;
    public String company_name;
    public String name;
    public String email;
    public String company_logo;
    public String user_logo;
    public UpdateProfileBody(String phone, String company_name, String name, String email, String company_logo, String user_logo) {
        this.phone = phone.replace("+", "").replace("(", "").replace(")", "").replace(" ", "").replace("-", "");
        this.name = name;
        this.company_name = company_name;
        this.email = email;
        this.company_logo = company_logo;
        this.user_logo = user_logo;
    }

    public JSONObject getBody() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("user_phone",phone);
            jsonObject.put("user_email",email);
            jsonObject.put("user_name",name);
            jsonObject.put("company_name",company_name);
            jsonObject.put("company_logo",company_logo);
            jsonObject.put("user_logo",user_logo);

        } catch (JSONException exception) {
            exception.printStackTrace();
        }
        return jsonObject;
    }
}
