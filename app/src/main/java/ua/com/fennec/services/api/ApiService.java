package ua.com.fennec.services.api;

import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import ua.com.fennec.Constants;
import ua.com.fennec.services.locale.LocaleUtils;
import ua.com.fennec.services.storage.StorageService;


public class ApiService  {

    public JSONObject api_params = new JSONObject();
    public Map<String, String> byte_params = new HashMap<String, String>();
    private Context context;
    private StorageService storage;

    public ApiService(Context context) {
        this.context = context;
        this.storage = new StorageService(context);
    }
    //String
    public interface ApiServiceOutput {
        void onResponse(JSONObject response, String URI);
        void onErrorResponse(String message);
    }

    public ApiServiceOutput OnRequestString;


    ///Byte
    public interface ApiServiceByteOutput {
        void onByteResponse(byte[] response);
    }

    ApiServiceByteOutput OnRequest_byte;




    public void send(final String URI, ApiRequestType type) {
        add_post_param("apikey", Constants.KEY_API);
        add_post_param("token", storage.getToken());
        final String json = api_params.toString();
        if (type == ApiRequestType.String) {
            RequestQueue queue = null;
            queue = Volley.newRequestQueue(context);

            String url = Constants.HOST + "/" + "?loc=" + URI;
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                Log.d(Constants.TAG, "ApiResponse " + url + "\n" + response);

                                OnRequestString.onResponse(new JSONObject(response), URI);

                            } catch (JSONException json_e) {
                                OnRequestString.onErrorResponse("Unexpected error from server");
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d(Constants.TAG, "ApiError " + url + "\n" + "error.getLocalizedMessage()");
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();

                    params.put("data", json);
                    Log.d(Constants.TAG, "ApiBody " + url + "\n" + params.toString());

                    clear_post_param();

                    return params;
                }
            };

            stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            queue.add(stringRequest);
            return;
        }

        if (type == ApiRequestType.Byte) {
            byte_params.put("data", json);
            InputStreamVolleyRequest request = new InputStreamVolleyRequest(Request.Method.POST, Constants.HOST + "?loc=" + URI,
                    new Response.Listener<byte[]>() {
                        @Override
                        public void onResponse(byte[] response) {
                            try {
                                if (response != null) {
                                    OnRequest_byte.onByteResponse(response);
                                }
                            } catch (Exception e) {
                                Log.d("KEY_ERROR", "UNABLE TO DOWNLOAD FILE");
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                }
            }, byte_params);
            RequestQueue mRequestQueue = Volley.newRequestQueue(context.getApplicationContext(), new HurlStack());
            mRequestQueue.add(request);
        }

    }

    public Boolean add_post_param(String param_name, String param_value) {
        try {
            api_params.put(param_name, param_value);
            return true;
        } catch (JSONException e) {
            return false;
        }
    }

    public Boolean add_post_param_jsonObj(String param_name, JSONObject param_value){
        try {
            api_params.put(param_name, param_value);
            return true;
        } catch (JSONException e) {
            return false;
        }
    }

    public Boolean add_post_param_jsonArr(String param_name, JSONArray param_value){
        try {
            api_params.put(param_name, param_value);
            return true;
        } catch (JSONException e) {
            return false;
        }
    }

    public void clear_post_param() {
        api_params = new JSONObject();
    }

    public Boolean set_post_params(String params) {
        try {
            api_params = new JSONObject(params);
            return true;
        } catch (JSONException e) {
            return false;
        }
    }

}

