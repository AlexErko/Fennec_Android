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
import ua.com.fennec.services.api.responseModels.ApiAnswerResponse;
import ua.com.fennec.services.api.responseModels.ApiConfirmAuthPhoneResponse;
import ua.com.fennec.services.api.responseModels.ApiGalleryAddResponse;
import ua.com.fennec.services.api.responseModels.ApiGetGalleryResponse;
import ua.com.fennec.services.api.responseModels.ApiGetProductResponse;
import ua.com.fennec.services.api.responseModels.ApiGetProfileResponse;


public class ApiService  {

    private Context context;
    private ApiClient client;
    private ApiImageClient imageClient;

    public ApiService(Context context) {
        this.context = context;
        this.client = new ApiClient(context);
        this.imageClient = new ApiImageClient(context);
    }

    public void confirmAuthPhone(ConfirmAuthPhoneBody body, ApiServiceOutput<ApiConfirmAuthPhoneResponse> output) {
        client.send(ApiEndpoints.f_api_auth_phone_confirm, body.getBody(), new ApiClient.ApiClientOutput() {
            @Override
            public void onResponse(String response) {
                ApiConfirmAuthPhoneResponse answer = null;
                try {
                    answer = new ApiConfirmAuthPhoneResponse(response);
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

    public void authPhone(AuthPhoneBody body, ApiServiceOutput<ApiAnswerResponse> output) {
        client.send(ApiEndpoints.f_api_auth_phone, body.getBody(), new ApiClient.ApiClientOutput() {
            @Override
            public void onResponse(String response) {
                ApiAnswerResponse answer = null;
                try {
                    answer = new ApiAnswerResponse(response);
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






    public void deleteImage(DeleteImageBody body, ApiServiceOutput<ApiAnswerResponse> output) {
        client.send(ApiEndpoints.f_api_gallery_del, body.getBody(), new ApiClient.ApiClientOutput() {
            @Override
            public void onResponse(String response) {
                ApiAnswerResponse answer = null;
                try {
                    answer = new ApiAnswerResponse(response);
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
    public void getProfile(ApiServiceOutput<ApiGetProfileResponse> output) {
        client.send(ApiEndpoints.f_api_profile_get, new JSONObject(), new ApiClient.ApiClientOutput() {
            @Override
            public void onResponse(String response) {
                ApiGetProfileResponse answer = null;
                Log.d(Constants.TAG, response);
                try {
                    answer = new ApiGetProfileResponse(response);
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
    public void getPrivateGallery(ApiServiceOutput<ApiGetGalleryResponse> output) {
        client.send(ApiEndpoints.f_api_gallery_private_get, new JSONObject(), new ApiClient.ApiClientOutput() {
            @Override
            public void onResponse(String response) {
                ApiGetGalleryResponse answer = null;
                Log.d(Constants.TAG, response);
                try {
                    answer = new ApiGetGalleryResponse(response);
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

    public void getPublicGallery(ApiServiceOutput<ApiGetGalleryResponse> output) {
        client.send(ApiEndpoints.f_api_gallery_public_get, new JSONObject(), new ApiClient.ApiClientOutput() {
            @Override
            public void onResponse(String response) {
                ApiGetGalleryResponse answer = null;
                Log.d(Constants.TAG, response);
                try {
                    answer = new ApiGetGalleryResponse(response);
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
    public void updateProfile(UpdateProfileBody body, ApiServiceOutput<ApiGetProfileResponse> output) {
        client.send(ApiEndpoints.f_api_profile_update, body.getBody(), new ApiClient.ApiClientOutput() {
            @Override
            public void onResponse(String response) {
                ApiGetProfileResponse answer = null;
                Log.d(Constants.TAG, response);
                try {
                    answer = new ApiGetProfileResponse(response);
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

    public void getProducts(Integer page, ApiServiceOutput<ApiGetProductResponse> output) {
        JSONObject object = new JSONObject();
        try {
            object.put("page", page);
        } catch (JSONException exception) {
            exception.printStackTrace();
        }

        client.send(ApiEndpoints.f_api_products_get, object, new ApiClient.ApiClientOutput() {
            @Override
            public void onResponse(String response) {
                ApiGetProductResponse answer = null;
                Log.d(Constants.TAG, response);
                try {
                    answer = new ApiGetProductResponse(response);
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

