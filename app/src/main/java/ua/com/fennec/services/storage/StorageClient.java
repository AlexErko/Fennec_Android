package ua.com.fennec.services.storage;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.Nullable;

public class StorageClient {


    private static final String PREFS_NAME = "Fennec";
    private static SharedPreferences sharedpreferences;
    public static StorageClient shared;


    StorageClient(Context context) {
        if (sharedpreferences == null) {
            this.sharedpreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
            StorageClient.shared = this;
        }
    }
    public void setString(Keys key, String val) {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(key.val,val);

        editor.commit();
    }


    public Integer getInteger(Keys key) {
        return sharedpreferences.getInt(key.val, 0);
    }
    public Boolean getBool(Keys key) {
        return sharedpreferences.getBoolean(key.val, false);
    }
    public String getString(Keys key) {
        return sharedpreferences.getString(key.val, "");
    }

    public enum Keys {

        TOKEN ("token"),
        PROFILE ("profile");

        private String val;

        Keys(String key) {
            this.val = key;
        }
    }

}
