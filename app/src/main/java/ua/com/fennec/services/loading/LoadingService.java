package ua.com.fennec.services.loading;

import android.view.View;

public class LoadingService {
    static private View indicator;

    static public void setIndicator(View view) {
        LoadingService.indicator = view;
    }
    static public void start() {
        indicator.setVisibility(View.VISIBLE);
    }
    static public void end() {
        indicator.setVisibility(View.GONE);
    }
}
