package ua.com.fennec.services.storage;

import android.content.Context;
import android.os.Build;
import android.telephony.TelephonyManager;

import java.util.UUID;

public class StorageService {

    StorageClient client;



    public StorageService(Context context) {
        this.client = new StorageClient(context);
    }




    public String getToken() {
        String  returnedValue = client.getString(StorageClient.Keys.TOKEN);
        if (returnedValue.equals("")) {

            String m_szDevIDShort = "35" + (Build.BOARD.length() % 10) + (Build.BRAND.length() % 10) + (Build.CPU_ABI.length() % 10) + (Build.DEVICE.length() % 10) + (Build.MANUFACTURER.length() % 10) + (Build.MODEL.length() % 10) + (Build.PRODUCT.length() % 10);

            String serial = null;
            try {
                serial = android.os.Build.class.getField("SERIAL").get(null).toString();
                return new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
            } catch (Exception exception) {
                serial = "serial";
            }
            returnedValue = new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
        }
        return  returnedValue;
    }
}