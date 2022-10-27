package ua.com.fennec.afterAuthFlow.profileModule;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import androidx.annotation.Nullable;

import ua.com.fennec.Constants;
import ua.com.fennec.afterAuthFlow.profileModule.interfaces.ProfileInteractorOutput;
import ua.com.fennec.services.api.ApiService;
import ua.com.fennec.services.api.ApiServiceOutput;
import ua.com.fennec.services.api.bodyModels.AuthPhoneBody;
import ua.com.fennec.services.api.bodyModels.UpdateProfileBody;
import ua.com.fennec.services.api.responseModels.ApiAnswerModel;
import ua.com.fennec.services.api.responseModels.ApiGetProfileModel;
import ua.com.fennec.services.loading.LoadingService;
import ua.com.fennec.services.message.MessageService;

public class ProfileInteractor {


    private ProfileInteractorOutput output;
    private ApiService apiService;
    private Context context;

    ProfileInteractor(ProfileInteractorOutput output, Context context) {
      this.output = output;
      this.context = context;
      this.apiService = new ApiService(context);
    };

    void updateProfile(UpdateProfileBody body) {
        LoadingService.start();
        apiService.updateProfile(body, new ApiServiceOutput<ApiGetProfileModel>() {
            @Nullable
            @Override
            public void onResponse(ApiGetProfileModel response) {
//                LoadingService.end();
            }
        });
    }
    void uploadCompanyLogo(Bitmap bitmap) {
        LoadingService.start();
        apiService.uploadCompanyImage(bitmap, new ApiServiceOutput<ApiAnswerModel>() {
            @Nullable
            @Override
            public void onResponse(ApiAnswerModel response) {
                LoadingService.end();
            }
        });
    }
    void getProfile() {
        LoadingService.start();
        apiService.getProfile(new ApiServiceOutput<ApiGetProfileModel>() {
            @Override
            public void onResponse(ApiGetProfileModel response) {
                Log.d(Constants.TAG, response.profile.company_logo);
                Log.d(Constants.TAG, response.profile.company_name);
                Log.d(Constants.TAG, response.profile.user_email);
                Log.d(Constants.TAG, response.profile.user_phone);
                Log.d(Constants.TAG, response.profile.user_name);

                LoadingService.end();
            }
        });
    }

}
