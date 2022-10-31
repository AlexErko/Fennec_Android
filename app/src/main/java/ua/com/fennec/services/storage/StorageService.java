package ua.com.fennec.services.storage;

import android.content.Context;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.Log;

import org.json.JSONException;

import java.util.UUID;

import ua.com.fennec.Constants;
import ua.com.fennec.models.Profile;

public class StorageService {

    private StorageClient client;
    public static StorageService shared;


    public static StorageService initShared(Context context) {
        StorageService.shared = new StorageService(context);
        return StorageService.shared;
    }
    private StorageService(Context context) {
        this.client = new StorageClient(context);
    }



    public void setToken(String token) {
        client.setString(StorageClient.Keys.TOKEN, token);
    }
    public void setProfile(Profile profile) {
        if (profile != null) {
            try {
                String profileString = profile.toJSON().toString();
                client.setString(StorageClient.Keys.PROFILE, profileString);
            } catch (JSONException exception) {
                exception.printStackTrace();
            }
        }
    }
    public Profile getProfile() {
        Profile profile = null;

        try {
            profile = new Profile(client.getString(StorageClient.Keys.PROFILE));
        } catch (JSONException exception) {
            exception.printStackTrace();
        }
        return profile;
    }
    public String getToken() {
        return client.getString(StorageClient.Keys.TOKEN);
    }
    public String getDeviceToken() {
            String m_szDevIDShort = "35" + (Build.BOARD.length() % 10) + (Build.BRAND.length() % 10) + (Build.CPU_ABI.length() % 10) + (Build.DEVICE.length() % 10) + (Build.MANUFACTURER.length() % 10) + (Build.MODEL.length() % 10) + (Build.PRODUCT.length() % 10);

            String serial = null;
            try {
                serial = android.os.Build.class.getField("SERIAL").get(null).toString();
                return new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
            } catch (Exception exception) {
                serial = "serial";
            }
            return new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
    }
}
