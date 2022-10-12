package ua.com.fennec.preAuthFlow.loginModule;

import android.content.Context;
import android.util.Log;

import org.json.JSONObject;

import ua.com.fennec.Constants;
import ua.com.fennec.preAuthFlow.loginModule.interfaces.LoginInteractorOutput;
import ua.com.fennec.services.api.ApiEndpoints;
import ua.com.fennec.services.api.ApiRequestType;
import ua.com.fennec.services.api.ApiService;
import ua.com.fennec.services.api.ApiServiceOutput;
import ua.com.fennec.services.api.bodyModels.AuthPhoneBody;
import ua.com.fennec.services.api.responseModels.ApiAnswerModel;

public class LoginInteractor {


    LoginInteractorOutput output;
    ApiService apiService;
    LoginInteractor(LoginInteractorOutput output, Context context) {
      this.output = output;
      this.apiService = new ApiService(context);
    };



    void authPhone(String phone) {
        apiService.authPhone(new AuthPhoneBody(phone), new ApiServiceOutput<ApiAnswerModel>() {
            @Override
            public void onResponse(ApiAnswerModel response) {
                if (response.status == 0) {
                    Log.d(Constants.TAG, "CONFIRM");
                }
            }
        });
    }

}
