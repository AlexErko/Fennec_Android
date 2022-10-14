package ua.com.fennec.preAuthFlow.codeModule;

import android.content.Context;

import ua.com.fennec.preAuthFlow.codeModule.interfaces.CodeInteractorOutput;
import ua.com.fennec.services.api.ApiService;
import ua.com.fennec.services.api.ApiServiceOutput;
import ua.com.fennec.services.api.bodyModels.AuthPhoneBody;
import ua.com.fennec.services.api.bodyModels.ConfirmAuthPhoneBody;
import ua.com.fennec.services.api.responseModels.ApiAnswerModel;
import ua.com.fennec.services.loading.LoadingService;

public class CodeInteractor {


    CodeInteractorOutput output;
    ApiService apiService;
    
    CodeInteractor(CodeInteractorOutput output, Context context) {
      this.output = output;
      this.apiService = new ApiService(context);
    };



    void confirmAuthPhone(String phone, String code) {
        LoadingService.start();
        ConfirmAuthPhoneBody body = new ConfirmAuthPhoneBody(phone, code);
        apiService.confirmAuthPhone(body, new ApiServiceOutput<ApiAnswerModel>() {
            @Override
            public void onResponse(ApiAnswerModel response) {
                LoadingService.end();
                if (response != null) {
                    if (response.status == 0) {
//                        output.codeDidSent(body.phone);
                    } else {

                    }
                }

            }
        });
    }

}
