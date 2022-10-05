package ua.com.fennec.services.locale;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.preference.PreferenceManager;
import android.view.ContextThemeWrapper;

import java.util.Locale;

public class LocaleUtils {

    private static Locale mLocale;

    public static void setLocale(Locale locale){
        mLocale = locale;
        if(mLocale != null){
            Locale.setDefault(mLocale);
        }
    }

    public static void updateConfiguration(ContextThemeWrapper wrapper){
        if(mLocale != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1){
            Configuration configuration = new Configuration();
            configuration.setLocale(mLocale);
            wrapper.applyOverrideConfiguration(configuration);
        }
    }

    public static void updateConfiguration(Application application, Configuration configuration){
        if(mLocale != null && Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1){
            Configuration config = new Configuration(configuration);
            config.locale = mLocale;
            Resources res = application.getBaseContext().getResources();
            res.updateConfiguration(configuration, res.getDisplayMetrics());
        }
    }

    public static void updateConfiguration(Activity activity, String language, String country){
        String lang = language;
        if (lang.equals("ua")) {
            lang = "uk";
        }
        Locale locale = new Locale(lang,country);

        setLocale(locale);
        if(mLocale != null){
            Resources res = activity.getResources();
            Configuration configuration = res.getConfiguration();
            configuration.locale = mLocale;
            res.updateConfiguration(configuration,res.getDisplayMetrics());
        }
    }




    public static String getPrefLangCode(Context context) {
        String lang = PreferenceManager.getDefaultSharedPreferences(context).getString("lang_code","ru");
        if (lang.equals("uk")) {
            lang = "ua";
        }

        return lang;
    }

    public static void setPrefLangCode(Context context, String mPrefLangCode) {

        String lang = "";
        if (mPrefLangCode.equals("ua")) {
            lang = "uk";
        }else{
            lang = mPrefLangCode;
        }
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putString("lang_code",lang);
        editor.commit();
    }

    public static String getPrefCountryCode(Context context) {
        String lang = PreferenceManager.getDefaultSharedPreferences(context).getString("country_code","ru");

        return lang;
    }

    public static void setPrefCountryCode(Context context, String mPrefCountryCode) {
        String lang = "";
        if (mPrefCountryCode.equals("ua")) {
            lang = "UA";
        }else{
            lang = mPrefCountryCode;
        }
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putString("country_code",lang);
        editor.commit();
    }
}
