package ua.com.fennec.customs.ui.sectionSwitcher;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.widget.TextViewCompat;

import ua.com.fennec.R;

public class SectionSwitcher extends LinearLayout {


    private FrameLayout selectedSectionBack;
    private TextView firstTitle;
    private TextView secondTitle;
    private OnSectionChangedListener changeListener;
    public SectionSwitcher(Context context) {
        super(context);
        config(null, context);
    }

    public SectionSwitcher(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        config(attrs, context);
    }

    public SectionSwitcher(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        config(attrs, context);
    }

    public SectionSwitcher(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        config(attrs, context);
    }



    private void config(AttributeSet attributeSet, Context context) {
        
        setOrientation(HORIZONTAL);
        firstTitle = new TextView(context);
        firstTitle.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1));
        firstTitle.setGravity(Gravity.CENTER);
        firstTitle.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setActiveSection(context, 0);
                if (changeListener != null) {
                    changeListener.sectionChanged(true);
                }
            }
        });
        TextViewCompat.setTextAppearance(firstTitle, R.style.H2Regular);
        addView(firstTitle);
        secondTitle = new TextView(context);
        secondTitle.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setActiveSection(context, 1);
                if (changeListener != null) {
                    changeListener.sectionChanged(false);
                }
            }
        });
        secondTitle.setGravity(Gravity.CENTER);
        secondTitle.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1));
        TextViewCompat.setTextAppearance(secondTitle, R.style.H2Regular);
        addView(secondTitle);



        if (attributeSet != null) {
            final TypedArray styledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.SectionSwitcher, 0, 0);
            try {
                int section = styledAttributes.getInt(R.styleable.SectionSwitcher_sectionIsActive,0);
                setActiveSection(context, section);
                setFirstTitle(styledAttributes.getString(R.styleable.SectionSwitcher_firstTitle));
                setSecondTitle(styledAttributes.getString(R.styleable.SectionSwitcher_secondTitle));
            } finally {
                styledAttributes.recycle();
            }

        }
    }

    public void setOnSectionChangeListener(OnSectionChangedListener listener) {
        changeListener = listener;
    }


    public void setFirstTitle(String str) {
        firstTitle.setText(str);
    }
    public void setSecondTitle(String str) {
        secondTitle.setText(str);
    }
    public void setActiveSection(Context context, int section) {
        this.post(new Runnable() {
            @Override
            public void run() {
                GradientDrawable shape =  new GradientDrawable();
                shape.setCornerRadius(getHeight() / 2);
                shape.setColor(ContextCompat.getColor(context, R.color.orange));
                firstTitle.setBackground(section == 0 ? shape : null);
                secondTitle.setBackground(section == 1 ? shape : null);
                GradientDrawable shape1 =  new GradientDrawable();
                shape1.setCornerRadius(getHeight() / 2);
                shape1.setColor(Color.parseColor("#FAF7F4"));
                setBackground(shape1);

            }
        });

        int textActivatedColor = ContextCompat.getColor(context, R.color.white);
        int textCommonColor = ContextCompat.getColor(context, R.color.medium_grey);


        firstTitle.setTextColor(section == 0 ? textActivatedColor : textCommonColor);
        secondTitle.setTextColor(section == 1 ? textActivatedColor : textCommonColor);
    }
}

