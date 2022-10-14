package ua.com.fennec.services.api;

import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
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
import ua.com.fennec.services.storage.StorageService;

public class ApiClient {
    public interface ApiClientOutput {
        void onResponse(String response, String URI);
        void onErrorResponse(String message, String URI);
    }

    private RequestQueue queue;
    private StorageService storage;


    public ApiClient(Context context) {
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
                        output.onResponse(response, URI);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        NetworkResponse errorRes = error.networkResponse;
                        String stringData = "";
                        if (errorRes != null && errorRes.data != null) {
                            try {
                                stringData = new String(errorRes.data, "UTF-8");
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                        }

//                        output.onErrorResponse("ApiError " + url + "\n" + "error.getLocalizedMessage()", URI);
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
