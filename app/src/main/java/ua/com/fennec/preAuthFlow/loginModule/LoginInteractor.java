package ua.com.fennec.preAuthFlow.loginModule;

import android.content.Context;

import org.json.JSONObject;

import ua.com.fennec.preAuthFlow.loginModule.interfaces.LoginInteractorOutput;
import ua.com.fennec.services.api.ApiEndpoints;
import ua.com.fennec.services.api.ApiRequestType;
import ua.com.fennec.services.api.ApiService;

public class LoginInteractor implements ApiService.ApiServiceOutput {


    LoginInteractorOutput output;
    ApiService apiService;
    LoginInteractor(LoginInteractorOutput output, Context context) {
      this.output = output;
      this.apiService = new ApiService();
      this.apiService.init(context);
        this.apiService.OnRequestString = this;
    };



    void authPhone(String phone) {
        apiService.add_post_param("user_phone", phone);
        apiService.send(ApiEndpoints.f_api_auth_phone, ApiRequestType.String);
    }

    @Override
    public void onResponse(JSONObject response, String URI) {

    }

    @Override
    public void onErrorResponse(String message) {

    }
}
