package ua.com.fennec.services.storage;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.Nullable;

public class StorageClient {


    private static final String PREFS_NAME = "Fennec";
    private SharedPreferences sharedpreferences;


    StorageClient(Context context) {
        this.sharedpreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }
    public <T> void setValue(Keys key, T val) {
        SharedPreferences.Editor editor = sharedpreferences.edit();

        if (val instanceof String) {
            editor.putString(key.val, (String) val);
        }
        if (val instanceof Integer) {
            editor.putInt(key.val, (Integer) val);
        }
        if (val instanceof Boolean) {
            editor.putBoolean(key.val, (Boolean) val);
        }
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

    enum Keys {

        TOKEN ("token");

        private String val;

        Keys(String key) {
            this.val = key;
        }
    }

}
