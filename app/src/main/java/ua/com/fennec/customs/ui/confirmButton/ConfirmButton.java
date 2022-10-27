package ua.com.fennec.customs.ui.confirmButton;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.widget.TextView;

import androidx.annotation.Nullable;

import ua.com.fennec.Constants;
import ua.com.fennec.R;

public class ConfirmButton extends androidx.appcompat.widget.AppCompatTextView {

    private boolean isActivated = false;

    public ConfirmButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        config(attrs,context);
    }

    public ConfirmButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        config(attrs,context);
    }

    public ConfirmButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs);
        config(attrs,context);
    }


    public void config(AttributeSet attributeSet, Context context) {

        final TypedArray styledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.ConfirmButton, 0, 0);
        try {
            setActivated(context, styledAttributes.getBoolean(R.styleable.ConfirmButton_isActive,false));
        } finally {
            styledAttributes.recycle();
        }
        setGravity(Gravity.CENTER);
        setTextColor(context.getColor(R.color.white));

    }

    public void setActivated(Context context, boolean activated) {
        isActivated = activated;
        Drawable back;
        if (activated == true) {
            back = context.getDrawable(R.drawable.button_filled_background);
        } else {
            back = context.getDrawable(R.drawable.button_disabled_background_light);
        }
        setBackground(back);

    }

    @Override
    public boolean isActivated() {
        return isActivated;
    }
}
