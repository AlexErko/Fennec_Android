package ua.com.fennec.preAuthFlow.loginModule;

import android.content.Context;

import ua.com.fennec.preAuthFlow.loginModule.interfaces.LoginInteractorOutput;
import ua.com.fennec.services.api.ApiService;
import ua.com.fennec.services.api.ApiServiceOutput;
import ua.com.fennec.services.api.bodyModels.AuthPhoneBody;
import ua.com.fennec.services.api.responseModels.ApiAnswerResponse;
import ua.com.fennec.services.loading.LoadingService;
import ua.com.fennec.services.message.MessageService;

public class LoginInteractor {


    private LoginInteractorOutput output;
    private ApiService apiService;
    private Context context;

    LoginInteractor(LoginInteractorOutput output, Context context) {
      this.output = output;
      this.context = context;
      this.apiService = new ApiService(context);
    };



    void authPhone(String phone) {
        LoadingService.start();
        AuthPhoneBody body = new AuthPhoneBody(phone);
        apiService.authPhone(body, new ApiServiceOutput<ApiAnswerResponse>() {
            @Override
            public void onResponse(ApiAnswerResponse response) {
                LoadingService.end();
                if (response != null) {
                    if (response.status == 0) {
                        output.codeDidSent(body.phone);
                    } else {
                        MessageService.showMessage(response.message, MessageService.Type.error, context);
                    }
                }

            }
        });
    }

}
