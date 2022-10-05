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


public class ApiService  {

    public JSONObject api_params = new JSONObject();
    public Map<String, String> byte_params = new HashMap<String, String>();
    public Context context;


    /*----------------------------------*/


    //String
    public interface OnRequestString {
        void onResponse(JSONObject response, String URI);
        void onErrorResponse();
    }

    OnRequestString OnRequestString;

    public void ListenerOnRequestString(OnRequestString onRequestString) {
        this.OnRequestString = onRequestString;
    }

    ///Byte
    public interface OnRequest_byte {
        void onResponse_byte(byte[] response);
    }

    OnRequest_byte OnRequest_byte;

    public void ListenerOnRequest_byte(OnRequest_byte onRequest_byte) {
        this.OnRequest_byte = onRequest_byte;

    }

    public void send(final String URI, String type) {
        final String json = api_params.toString();
        if (type.equals("str") || type.equals("String")) {
            RequestQueue queue = null;
            queue = Volley.newRequestQueue(context);

            StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.HOST + "/" + LocaleUtils.getPrefLangCode(context) + "/" + "?loc=" + URI,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                if (new JSONObject(response).getInt("status") == 1) {

                                }
                                OnRequestString.onResponse(new JSONObject(response), URI);

                            } catch (JSONException json_e) {
                                OnRequestString.onErrorResponse();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("######", "qqwe");

                            NetworkResponse errorRes = error.networkResponse;
                            String stringData = "";
                            if (errorRes != null && errorRes.data != null) {
                                try {
                                    stringData = new String(errorRes.data, "UTF-8");
                                } catch (UnsupportedEncodingException e) {
                                    e.printStackTrace();
                                }
                            }

                            OnRequestString.onErrorResponse();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("data", json);
                    clear_post_param();

                    return params;
                }
            };

            stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            queue.add(stringRequest);
            return;
        }

        if (type.equals("byte")) {
            byte_params.put("data", json);
            InputStreamVolleyRequest request = new InputStreamVolleyRequest(Request.Method.POST, Constants.HOST + "?loc=" + URI,
                    new Response.Listener<byte[]>() {
                        @Override
                        public void onResponse(byte[] response) {
                            try {
                                if (response != null) {
                                    OnRequest_byte.onResponse_byte(response);
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

    /*----------------------------------*/

    public void init(Context context) {
        this.context = context;
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

