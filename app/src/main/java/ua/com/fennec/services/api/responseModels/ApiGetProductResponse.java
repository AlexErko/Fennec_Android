package ua.com.fennec.services.api.responseModels;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import ua.com.fennec.models.Product;

public class ApiGetProductResponse {
    public ApiAnswerResponse answer;
    public ArrayList<Product> products;
    public Integer total;


    public ApiGetProductResponse(String response) throws JSONException {
        answer = new ApiAnswerResponse(response);
        JSONObject jsonObject = new JSONObject(response);
        JSONObject result = jsonObject.getJSONObject("result");
        total = result.getInt("total");
        JSONArray array = result.getJSONArray("rows");
        products = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            JSONObject product = array.getJSONObject(i);
            products.add(new Product(product));
        }
    }
}
