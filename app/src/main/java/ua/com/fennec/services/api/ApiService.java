package ua.com.fennec.services.api;

import android.content.Context;

import org.json.JSONException;

import ua.com.fennec.services.api.bodyModels.AuthPhoneBody;
import ua.com.fennec.services.api.responseModels.ApiAnswerModel;


public class ApiService  {

    private Context context;
    private ApiClient client;

    public ApiService(Context context) {
        this.context = context;
        this.client = new ApiClient(context);
    }

    public void authPhone(AuthPhoneBody body, ApiServiceOutput<ApiAnswerModel> output) {
        client.send(ApiEndpoints.f_api_auth_phone, body.getBody(), new ApiClient.ApiClientOutput() {
            @Override
            public void onResponse(String response, String URI) {
                ApiAnswerModel answer = null;
                try {
                    answer = new ApiAnswerModel(response);
                } catch (JSONException exception) {
                    exception.printStackTrace();
                }
                output.onResponse(answer);

            }
            @Override
            public void onErrorResponse(String message, String URI) {

            }
        });
    }

}

