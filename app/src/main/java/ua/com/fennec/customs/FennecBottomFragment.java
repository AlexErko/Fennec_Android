package ua.com.fennec.customs;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

import ua.com.fennec.R;
import ua.com.fennec.services.KeyboardService;

public class FennecBottomFragment extends Fragment {
    public void show(View rootView) {
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
    public void dismiss() {
        getParentFragmentManager().beginTransaction().remove(this).commitAllowingStateLoss();
    }
    public void hide(View rootView) {
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
                        if (this != null) {
                            if (rootView != null) {
                                rootView.setVisibility(View.GONE);
                                ViewParent parent = rootView.getParent();
                                if (parent != null) {
                                    ((ViewGroup) parent.getParent()).removeView((View) rootView.getParent());
                                    dismiss();
                                }
                            }
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
        });
    }
}
