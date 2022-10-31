package ua.com.fennec.globalModules.getPhotoModule;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import ua.com.fennec.R;
import ua.com.fennec.customs.FennecBottomFragment;
import ua.com.fennec.customs.swipeGesture.OnSwipeTouchListener;
import ua.com.fennec.preAuthFlow.PreAuthRouter;
import ua.com.fennec.preAuthFlow.loginModule.LoginFragment;
import ua.com.fennec.services.KeyboardService;

final public class GetPhotoFragment extends Fragment {

    private View rootView;

    //
    public static GetPhotoFragment showFragment(AppCompatActivity activity) {
        int layoutId = View.generateViewId();
        final ViewGroup viewGroup = (ViewGroup) ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0);
        FrameLayout layout = new FrameLayout(activity);
        layout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        layout.setClickable(true);
        layout.setId(layoutId);
        viewGroup.addView(layout);


        GetPhotoFragment photoFragment = new GetPhotoFragment();

        FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);
        ft.add(layoutId, photoFragment).commitAllowingStateLoss();




        return photoFragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_get_photo, container, false);

        rootView.findViewById(R.id.bottomView).setOnTouchListener(new OnSwipeTouchListener() {
            public boolean onSwipeBottom() {
                hide();
                return true;
            }
        });
        rootView.findViewById(R.id.shadow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hide();
            }
        });
        return rootView;
    }
    private void show() {
        LinearLayout view = rootView.findViewById(R.id.bottomView);
        KeyboardService.hideKeyboard(getActivity());

        view.post(new Runnable() {
            @Override
            public void run() {
                int height = view.getHeight();
                rootView.findViewById(R.id.shadow).animate().alpha(1).start();
                ObjectAnimator.ofFloat(view,"translationY",-height).setDuration(300).start();
            }
        });
    }
    private void dismiss() {
        getParentFragmentManager().beginTransaction().remove(this).commitAllowingStateLoss();
    }
    private void hide() {
        LinearLayout view = rootView.findViewById(R.id.bottomView);

        KeyboardService.hideKeyboard(getActivity());
        view.post(new Runnable() {
            @Override
            public void run() {
                int height = view.getHeight();
                rootView.findViewById(R.id.shadow).animate().alpha(0).start();
                ObjectAnimator objectAnimator  = ObjectAnimator.ofFloat(view,"translationY",height).setDuration(300);
                objectAnimator.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        rootView.setVisibility(View.GONE);
                        ((ViewGroup) rootView.getParent().getParent()).removeView((View) rootView.getParent());
                        dismiss();

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
        });
    }
}
