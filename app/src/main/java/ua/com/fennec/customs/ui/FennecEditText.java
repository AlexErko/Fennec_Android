package ua.com.fennec.customs.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;

import ua.com.fennec.Constants;
import ua.com.fennec.R;

public class FennecEditText extends androidx.appcompat.widget.AppCompatEditText {

    private Boolean isEditable;

    public FennecEditText(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        final TypedArray styledAttributes = context.obtainStyledAttributes(attrs, R.styleable.FennecEditText, 0, 0);
        try {
            setEditable(context, styledAttributes.getBoolean(R.styleable.FennecEditText_isEditable,false));
        } finally {
            styledAttributes.recycle();
        }
//
//
//

        Boolean isDark = AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES;
        if (isDark) {
            Log.d(Constants.TAG, "DART");
            setTextColor(context.getColor(R.color.white));
        } else {
            Log.d(Constants.TAG, "LIGJT");
            setTextColor(context.getColor(R.color.black));
            setHintTextColor(context.getColor(R.color.dark_gray));
        }


    }


    public void setEditable(Context context, boolean editable) {
        isEditable = editable;
        Boolean isDark = AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES;
        Drawable back;
        if (isEditable == true) {
            setFocusableInTouchMode(true);
            if (isDark) {
                Log.d(Constants.TAG, "DART");
                back = context.getDrawable(R.drawable.input_editable_background_dark);
            } else {
                Log.d(Constants.TAG, "LIGJT");
                back = context.getDrawable(R.drawable.input_editable_background_light);
            }
        } else {
            setFocusable(false);
            if (isDark) {
                back = context.getDrawable(R.drawable.input_background_dark);
            } else {
                back = context.getDrawable(R.drawable.input_background_light);
            }
        }
        setBackground(back);
    }
    public boolean isEditable() {
        return isEditable;
    }

}
