package ua.com.fennec.globalModules.getPhotoModule;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import androidx.annotation.Nullable;

import ua.com.fennec.Constants;
import ua.com.fennec.R;
import ua.com.fennec.globalModules.getPhotoModule.interfaces.GetPhotoInteractorOutput;
import ua.com.fennec.services.api.ApiService;
import ua.com.fennec.services.api.ApiServiceOutput;
import ua.com.fennec.services.api.bodyModels.DeleteImageBody;
import ua.com.fennec.services.api.responseModels.ApiAnswerModel;
import ua.com.fennec.services.api.responseModels.ApiGalleryAddResponse;
import ua.com.fennec.services.api.responseModels.ApiGetGallery;
import ua.com.fennec.services.loading.LoadingService;
import ua.com.fennec.services.message.MessageService;

public class GetPhotoInteractor {

    private ApiService apiService;
    private Context context;
    private GetPhotoInteractorOutput output;

    GetPhotoInteractor(Context context, GetPhotoInteractorOutput output) {
        this.context = context;
        this.apiService = new ApiService(context);
        this.output = output;
    };


    void deleteImage(String filename) {
        LoadingService.start();
        apiService.deleteImage(new DeleteImageBody(filename), new ApiServiceOutput<ApiAnswerModel>() {
            @Nullable
            @Override
            public void onResponse(ApiAnswerModel response) {
                if (response != null) {
                    if (response.status == 0) {
                        getPrivateGallery();
                    } else {
                        MessageService.showMessage(response.message, MessageService.Type.error, context);
                    }
                }
            }
        });
    }
    void uploadImage(Bitmap bitmap) {
        LoadingService.start();
        apiService.uploadImage(bitmap, new ApiServiceOutput<ApiGalleryAddResponse>() {
            @Nullable
            @Override
            public void onResponse(ApiGalleryAddResponse response) {
                if (response != null) {
                    if (response.answer.status == 0) {
                        Log.d(Constants.TAG, "response path " + response.filepath);
                            output.imageAdded(response.filepath);
                    } else {
                        MessageService.showMessage(response.answer.message, MessageService.Type.error, context);
                    }
                }
                LoadingService.end();
            }
        });
    }

    void getPrivateGallery() {
        apiService.getPrivateGallery(new ApiServiceOutput<ApiGetGallery>() {
            @Nullable
            @Override
            public void onResponse(ApiGetGallery response) {
                if (response != null) {
                    if (response.answer.status == 0) {
                        if (response.photos != null) {
                            output.privateGalleryGot(response.photos);
                        } else {
                            MessageService.showMessage(context.getString(R.string.unexpected_response_from_the_server), MessageService.Type.error, context);
                        }
                    } else {
                        MessageService.showMessage(response.answer.message, MessageService.Type.error, context);
                    }
                }
                LoadingService.end();

            }
        });
    }
    void getPublicGallery() {
        apiService.getPublicGallery(new ApiServiceOutput<ApiGetGallery>() {
            @Nullable
            @Override
            public void onResponse(ApiGetGallery response) {
                if (response != null) {
                    if (response.answer.status == 0) {
                        if (response.photos != null) {
                            output.publicGalleryGot(response.photos);
                        } else {
                            MessageService.showMessage(context.getString(R.string.unexpected_response_from_the_server), MessageService.Type.error, context);
                        }
                    } else {
                        MessageService.showMessage(response.answer.message, MessageService.Type.error, context);
                    }
                }
                LoadingService.end();

            }
        });
    }
}
