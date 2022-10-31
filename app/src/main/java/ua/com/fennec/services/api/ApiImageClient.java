package ua.com.fennec.services.api;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import ua.com.fennec.Constants;
import ua.com.fennec.R;
import ua.com.fennec.services.message.MessageService;
import ua.com.fennec.services.storage.StorageClient;
import ua.com.fennec.services.storage.StorageService;

public class ApiImageClient {
    private RequestQueue queue;
    private Context context;

    public ApiImageClient(Context context) {
        this.context = context;
        this.queue = Volley.newRequestQueue(context);
    }



    public void send(String URI, Bitmap image, ApiClient.ApiClientOutput output) {
        String url = Constants.HOST + "/" + "?loc=" + URI;


        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, url,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        String res = new String(response.data);
                        output.onResponse(res);
                        Log.d(Constants.TAG, res);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error.networkResponse == null) {
                            MessageService.showMessage(context.getString(R.string.no_internet_connection), MessageService.Type.error, context);
                        } else {
                            MessageService.showMessage(context.getString(R.string.unexpected_response_from_the_server) + ". " + context.getString(R.string.please_try_again_later) + ".", MessageService.Type.error, context);
                        }
                        output.onErrorResponse();
                        Log.d(Constants.TAG, "ApiError " + url + "\n" + error.getMessage());
                    }
                }) {
            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                long imagename = System.currentTimeMillis();
                params.put("image", new DataPart(imagename + ".jpeg", getFileDataFromDrawable(image)));
                return params;
            }

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("data", "{\"token\":\"" + StorageService.shared.getToken() + "\"," + "\"apikey\":\"" + Constants.KEY_API + "\"}");
                Log.d(Constants.TAG, "ApiImageBody " + url + "\n" + params.toString());
                return params;
            }
        };
        volleyMultipartRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(volleyMultipartRequest);

    }
    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

}
