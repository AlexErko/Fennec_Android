package ua.com.fennec.services.message;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.res.ResourcesCompat;

import ua.com.fennec.Constants;
import ua.com.fennec.R;
import ua.com.fennec.services.unit.Unit;


public class MessageService {
    static private FrameLayout messageLayout;
    static private LinearLayout messageView;
    static public void setView(FrameLayout view) {
        MessageService.messageLayout = view;
    }


    static public void showMessage(String message, Type type, Context context) {
        if (messageView != null) {
            messageLayout.removeView(messageView);
            messageView = null;
        }

        LinearLayout newMessageView = new LinearLayout(context);
        newMessageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hide(newMessageView);
            }
        });
        messageView = newMessageView;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(Unit.dpToPixel(17, context),Unit.dpToPixel(72, context),Unit.dpToPixel(17, context), 0);
        messageView.setLayoutParams(params);
        messageView.setAlpha(0f);
        messageView.setOrientation(LinearLayout.HORIZONTAL);
        messageLayout.addView(messageView);


        ImageView imageView = new ImageView(context);
        params = new LinearLayout.LayoutParams(Unit.dpToPixel(24, context), Unit.dpToPixel(24, context));
        params.setMargins(Unit.dpToPixel(16, context),Unit.dpToPixel(12, context),Unit.dpToPixel(8, context), 0);
        imageView.setLayoutParams(params);
        messageView.addView(imageView);



        LinearLayout titleAndTextLayout = new LinearLayout(context);
        params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(0,Unit.dpToPixel(12, context),0, Unit.dpToPixel(16, context));
        titleAndTextLayout.setLayoutParams(params);
        titleAndTextLayout.setOrientation(LinearLayout.VERTICAL);
        messageView.addView(titleAndTextLayout);


        TextView messageTitle = new TextView(context);
        params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, Unit.dpToPixel(22, context));
        messageTitle.setLayoutParams(params);
        Typeface typeface = ResourcesCompat.getFont(context, R.font.lato_bold);
        messageTitle.setTypeface(typeface);
        messageTitle.setTextSize(16);
        messageTitle.setGravity(Gravity.CENTER_VERTICAL);
        titleAndTextLayout.addView(messageTitle);


        TextView messageTextView = new TextView(context);
        params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(0,Unit.dpToPixel(5, context),0, 0);
        messageTextView.setLayoutParams(params);
        messageTextView.setTypeface(ResourcesCompat.getFont(context, R.font.lato_regular));
        messageTextView.setTextSize(14);
        titleAndTextLayout.addView(messageTextView);

        Drawable back;
        Drawable image;
        int titleColor;
        String title;
        int textColor;
        boolean isDark = (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES);
        if (isDark) {
            textColor = context.getColor(R.color.light_grey_2);
        } else {
            textColor = context.getColor(R.color.dark_gray);
        }
        switch (type) {
            case info:
                image = context.getDrawable(R.drawable.image_alert_info);
                title = context.getString(R.string.info);
                if (isDark) {
                    titleColor = Color.parseColor("#FFFFFF");
                    back = context.getDrawable(R.drawable.message_info_back_dark);
                } else {
                    titleColor = Color.parseColor("#2952CC");
                    back = context.getDrawable(R.drawable.message_info_back_light);
                }
                break;
            case error:
                image = context.getDrawable(R.drawable.image_alert_error);
                title = context.getString(R.string.error);
                if (isDark) {
                    titleColor = Color.parseColor("#FFFFFF");
                    back = context.getDrawable(R.drawable.message_error_back_dark);
                } else {
                    titleColor = Color.parseColor("#A73636");
                    back = context.getDrawable(R.drawable.message_error_back_light);
                }
                break;
            case success:
                title = context.getString(R.string.successful_operation);
                image = context.getDrawable(R.drawable.image_alert_success);
                if (isDark) {
                    titleColor = Color.parseColor("#FFFFFF");
                    back = context.getDrawable(R.drawable.message_success_back_dark);
                } else {
                    titleColor = Color.parseColor("#317159");
                    back = context.getDrawable(R.drawable.message_success_back_light);
                }
                break;
            case warning:
                title = context.getString(R.string.warning);
                image = context.getDrawable(R.drawable.image_alert_warning);
                if (isDark) {
                    titleColor = Color.parseColor("#FFFFFF");
                    back = context.getDrawable(R.drawable.message_warning_back_dark);
                } else {
                    titleColor = Color.parseColor("#996A13");
                    back = context.getDrawable(R.drawable.message_warning_back_light);
                }
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }
        imageView.setImageDrawable(image);
        messageTitle.setTextColor(titleColor);
        messageTitle.setText(title);
        messageTextView.setText(message);
        messageTextView.setTextColor(textColor);
        messageView.setBackground(back);


        ObjectAnimator animator = ObjectAnimator.ofFloat(messageView,"alpha",1f).setDuration(300);
        animator.start();


        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (messageView.equals(newMessageView)) {
                    hide(messageView);
                }
            }
        }, 5000);
    }

    private static void hide(View view) {
        ObjectAnimator objectAnimator  = ObjectAnimator.ofFloat(view,"alpha",0f).setDuration(300);
        objectAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                ViewGroup parent = (ViewGroup) view.getParent();
                if (parent != null) {
                    parent.removeView(view);
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        objectAnimator.start();

    }

    public enum Type {
        error,
        success,
        warning,
        info
    }
}
