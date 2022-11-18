package ua.com.fennec.services.api.bodyModels;

import org.json.JSONException;
import org.json.JSONObject;

public class DeleteImageBody {
    public String filename;


    public DeleteImageBody(String filename) {
        this.filename = filename;
    }

    public JSONObject getBody() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("filename",filename);
        } catch (JSONException exception) {
            exception.printStackTrace();
        }
        return jsonObject;
    }
}
