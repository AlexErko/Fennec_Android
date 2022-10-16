package ua.com.fennec.services.unit;

import android.content.Context;
import android.util.DisplayMetrics;

public class Unit {
    public static int dpToPixel(int dp, Context context){
        return dp * (context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }
}
