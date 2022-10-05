package ua.com.fennec.customs.ui.confirmButton;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;

import androidx.annotation.Nullable;

import ua.com.fennec.R;

public class ConfirmButton extends androidx.appcompat.widget.AppCompatTextView {


    public boolean isActivated = false;
    public ConfirmButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setGravity(Gravity.CENTER);
        setTextColor(context.getColor(R.color.white));
        setBackground(context.getDrawable(R.drawable.button_disabled_background_light));
    }
}
