package ua.com.fennec.services.api;

import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import ua.com.fennec.Constants;
import ua.com.fennec.R;
import ua.com.fennec.services.message.MessageService;
import ua.com.fennec.services.storage.StorageService;

public class ApiClient {
    public interface ApiClientOutput {
        void onResponse(String response);
        void onErrorResponse();
    }

    private RequestQueue queue;
    private StorageService storage;
    private Context context;

    public ApiClient(Context context) {
        this.context = context;
        this.queue = Volley.newRequestQueue(context);
        this.storage = new StorageService(context);
    }



    public void send(String URI, JSONObject body, ApiClientOutput output) {
        addParam(body, "apikey", Constants.KEY_API);
        addParam(body, "token", storage.getToken());
        String url = Constants.HOST + "/" + "?loc=" + URI;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(Constants.TAG, "ApiResponse " + url + "\n" + response);
                        output.onResponse(response);
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
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("data", body.toString());
                Log.d(Constants.TAG, "ApiBody " + url + "\n" + params.toString());

                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(stringRequest);
        return;
    }



    private void addParam(JSONObject jsonObject, String key, String val) {
        try {
            jsonObject.put(key,val);
        } catch (JSONException exception) {
            exception.printStackTrace();
        }

    }
}
