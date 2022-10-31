package ua.com.fennec.services;

import android.app.Activity;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class KeyboardService {

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        View view1 = activity.getCurrentFocus();
        if (view1 instanceof EditText) {
            EditText editText = ((EditText) view1);
            editText.setFocusableInTouchMode(true);
            editText.clearFocus();
        }
        imm.hideSoftInputFromWindow(((View) view).getWindowToken(), 0);
    }
}
