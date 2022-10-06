package ua.com.fennec.customs.ui.confirmButton;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;

import androidx.annotation.Nullable;

import ua.com.fennec.R;

public class ConfirmButton extends androidx.appcompat.widget.AppCompatTextView {


    private boolean isActivated = false;
    private Context context;
    public ConfirmButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        setGravity(Gravity.CENTER);
        setTextColor(context.getColor(R.color.white));
        setBackground(context.getDrawable(R.drawable.button_disabled_background_light));
    }

    @Override
    public void setActivated(boolean activated) {
        isActivated = activated;
        if (activated == true) {
            setBackground(context.getDrawable(R.drawable.button_filled_background));
        } else {
            setBackground(context.getDrawable(R.drawable.button_disabled_background_light));
        }
    }

    @Override
    public boolean isActivated() {
        return isActivated;
    }
}
