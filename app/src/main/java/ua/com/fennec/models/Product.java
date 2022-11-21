package ua.com.fennec.models;

import org.json.JSONException;
import org.json.JSONObject;

public class Product {
    public String product_id;
    public String name;
    public String image;
    public String group_id;
    public String price;
    public String self_price;
    public String bonus;
    public  String enabletime;
    public  String enabletime_begin;
    public String enabletime_end;

    public Product(JSONObject jsonObject) throws JSONException {
        product_id = jsonObject.getString("product_id");
        name = jsonObject.getString("name");
        image = jsonObject.getString("image");
        group_id = jsonObject.getString("group_id");
        price = jsonObject.getString("price");
        self_price = jsonObject.getString("self_price");
        bonus = jsonObject.getString("bonus");
        enabletime = jsonObject.getString("enabletime");
        enabletime_begin = jsonObject.getString("enabletime_begin");
        enabletime_end = jsonObject.getString("enabletime_end");
    }
}

