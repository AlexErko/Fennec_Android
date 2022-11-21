package ua.com.fennec.preAuthFlow.codeModule;

import android.content.Context;

import ua.com.fennec.preAuthFlow.codeModule.interfaces.CodeInteractorOutput;
import ua.com.fennec.services.api.ApiService;
import ua.com.fennec.services.api.ApiServiceOutput;
import ua.com.fennec.services.api.bodyModels.ConfirmAuthPhoneBody;
import ua.com.fennec.services.api.responseModels.ApiConfirmAuthPhoneResponse;
import ua.com.fennec.services.loading.LoadingService;
import ua.com.fennec.services.message.MessageService;

public class CodeInteractor {


    private CodeInteractorOutput output;
    private ApiService apiService;
    private Context context;
    CodeInteractor(CodeInteractorOutput output, Context context) {
      this.output = output;
      this.context = context;
      this.apiService = new ApiService(context);
    };



    void confirmAuthPhone(String phone, String code) {
        LoadingService.start();
        ConfirmAuthPhoneBody body = new ConfirmAuthPhoneBody(phone, code);
        apiService.confirmAuthPhone(body, new ApiServiceOutput<ApiConfirmAuthPhoneResponse>() {
            @Override
            public void onResponse(ApiConfirmAuthPhoneResponse response) {
                LoadingService.end();
                if (response != null) {
                    if (response.answer.status == 0) {
                        output.phoneDidConfirmed(phone, response.token);
                    } else {
                        MessageService.showMessage(response.answer.message, MessageService.Type.error, context);
                    }
                }

            }
        });
    }

}
