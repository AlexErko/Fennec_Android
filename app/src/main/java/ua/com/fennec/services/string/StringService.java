package ua.com.fennec.services.string;

public class StringService {

    public static Boolean isNull(String str) {
        Boolean isNull = true;

        if (str != null) {
            if (str.equals("null") == false) {
                isNull = false;
            }
        }

        return isNull;
    }
}
