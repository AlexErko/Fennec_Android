package ua.com.fennec.services.api;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import ua.com.fennec.Constants;
import ua.com.fennec.services.api.bodyModels.AuthPhoneBody;
import ua.com.fennec.services.api.bodyModels.ConfirmAuthPhoneBody;
import ua.com.fennec.services.api.bodyModels.DeleteImageBody;
import ua.com.fennec.services.api.bodyModels.UpdateProfileBody;
import ua.com.fennec.services.api.responseModels.ApiAnswerModel;
import ua.com.fennec.services.api.responseModels.ApiConfirmAuthPhoneModel;
import ua.com.fennec.services.api.responseModels.ApiGalleryAddResponse;
import ua.com.fennec.services.api.responseModels.ApiGetGallery;
import ua.com.fennec.services.api.responseModels.ApiGetProfileModel;


public class ApiService  {

    private Context context;
    private ApiClient client;
    private ApiImageClient imageClient;

    public ApiService(Context context) {
        this.context = context;
        this.client = new ApiClient(context);
        this.imageClient = new ApiImageClient(context);
    }

    public void confirmAuthPhone(ConfirmAuthPhoneBody body, ApiServiceOutput<ApiConfirmAuthPhoneModel> output) {
        client.send(ApiEndpoints.f_api_auth_phone_confirm, body.getBody(), new ApiClient.ApiClientOutput() {
            @Override
            public void onResponse(String response) {
                ApiConfirmAuthPhoneModel answer = null;
                try {
                    answer = new ApiConfirmAuthPhoneModel(response);
                } catch (JSONException exception) {
                    exception.printStackTrace();
                }
                output.onResponse(answer);

            }
            @Override
            public void onErrorResponse() {
                output.onResponse(null);
            }
        });
    }

    public void authPhone(AuthPhoneBody body, ApiServiceOutput<ApiAnswerModel> output) {
        client.send(ApiEndpoints.f_api_auth_phone, body.getBody(), new ApiClient.ApiClientOutput() {
            @Override
            public void onResponse(String response) {
                ApiAnswerModel answer = null;
                try {
                    answer = new ApiAnswerModel(response);
                } catch (JSONException exception) {
                    exception.printStackTrace();
                }
                output.onResponse(answer);

            }
            @Override
            public void onErrorResponse() {
                output.onResponse(null);
            }
        });
    }






    public void deleteImage(DeleteImageBody body, ApiServiceOutput<ApiAnswerModel> output) {
        client.send(ApiEndpoints.f_api_gallery_del, body.getBody(), new ApiClient.ApiClientOutput() {
            @Override
            public void onResponse(String response) {
                ApiAnswerModel answer = null;
                try {
                    answer = new ApiAnswerModel(response);
                } catch (JSONException exception) {
                    exception.printStackTrace();
                }
                output.onResponse(answer);

            }
            @Override
            public void onErrorResponse() {
                output.onResponse(null);
            }
        });
    }
    public void uploadImage(Bitmap image, ApiServiceOutput<ApiGalleryAddResponse> output) {
        imageClient.send(ApiEndpoints.f_api_gallery_add, image, new ApiClient.ApiClientOutput() {
            @Override
            public void onResponse(String response) {
                ApiGalleryAddResponse answer = null;
                try {
                    answer = new ApiGalleryAddResponse(response);
                } catch (JSONException exception) {
                    exception.printStackTrace();
                }
                output.onResponse(answer);

            }
            @Override
            public void onErrorResponse() {
                output.onResponse(null);
            }
        });
    }
    public void getProfile(ApiServiceOutput<ApiGetProfileModel> output) {
        client.send(ApiEndpoints.f_api_profile_get, new JSONObject(), new ApiClient.ApiClientOutput() {
            @Override
            public void onResponse(String response) {
                ApiGetProfileModel answer = null;
                Log.d(Constants.TAG, response);
                try {
                    answer = new ApiGetProfileModel(response);
                } catch (JSONException exception) {
                    Log.d(Constants.TAG, "Catch Error GetProfile");
                    exception.printStackTrace();
                }
                output.onResponse(answer);

            }
            @Override
            public void onErrorResponse() {
                output.onResponse(null);
            }
        });
    }
    public void getPrivateGallery(ApiServiceOutput<ApiGetGallery> output) {
        client.send(ApiEndpoints.f_api_gallery_private_get, new JSONObject(), new ApiClient.ApiClientOutput() {
            @Override
            public void onResponse(String response) {
                ApiGetGallery answer = null;
                Log.d(Constants.TAG, response);
                try {
                    answer = new ApiGetGallery(response);
                } catch (JSONException exception) {
                    Log.d(Constants.TAG, "Catch Error getPublicGallery");
                    exception.printStackTrace();
                }
                output.onResponse(answer);

            }
            @Override
            public void onErrorResponse() {
                output.onResponse(null);
            }
        });
    }

    public void getPublicGallery(ApiServiceOutput<ApiGetGallery> output) {
        client.send(ApiEndpoints.f_api_gallery_public_get, new JSONObject(), new ApiClient.ApiClientOutput() {
            @Override
            public void onResponse(String response) {
                ApiGetGallery answer = null;
                Log.d(Constants.TAG, response);
                try {
                    answer = new ApiGetGallery(response);
                } catch (JSONException exception) {
                    Log.d(Constants.TAG, "Catch Error getPublicGallery");
                    exception.printStackTrace();
                }
                output.onResponse(answer);

            }
            @Override
            public void onErrorResponse() {
                output.onResponse(null);
            }
        });
    }
    public void updateProfile(UpdateProfileBody body, ApiServiceOutput<ApiGetProfileModel> output) {
        client.send(ApiEndpoints.f_api_profile_update, body.getBody(), new ApiClient.ApiClientOutput() {
            @Override
            public void onResponse(String response) {
                ApiGetProfileModel answer = null;
                Log.d(Constants.TAG, response);
                try {
                    answer = new ApiGetProfileModel(response);
                } catch (JSONException exception) {
                    Log.d(Constants.TAG, "Catch Error updateProfile");
                    exception.printStackTrace();
                }
                output.onResponse(answer);

            }
            @Override
            public void onErrorResponse() {
                output.onResponse(null);
            }
        });
    }
}

