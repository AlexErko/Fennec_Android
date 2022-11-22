package ua.com.fennec.modules.afterAuthFlow.menuModule;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ua.com.fennec.R;
import ua.com.fennec.modules.afterAuthFlow.AfterAuthRouter;
import ua.com.fennec.services.unit.Unit;

public class MenuFragment extends Fragment implements MenuRecycleAdapterOutput {

    private View rootView;
    private AfterAuthRouter router;



    public MenuFragment(AfterAuthRouter router) {
        this.router = router;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        hide();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_menu, container, false);
        RecyclerView recyclerView = rootView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        recyclerView.setAdapter(new MenuRecycleAdapter(getContext(), MenuButton.getArray(), this));



        rootView.findViewById(R.id.shadow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hide();
            }
        });
        return rootView;
    }







    public void show() {
        FragmentManager fragmentManager = getParentFragmentManager();
        fragmentManager.beginTransaction().show(this).commitAllowingStateLoss();

        ConstraintLayout view = rootView.findViewById(R.id.menuView);
        ValueAnimator anim = ValueAnimator.ofInt(view.getMeasuredHeight(), Unit.dpToPixel(440, getContext()));
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int val = (Integer) valueAnimator.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                layoutParams.height = val;
                view.setLayoutParams(layoutParams);
            }

        });
        anim.setDuration(200);
        anim.start();
        rootView.findViewById(R.id.shadow).animate().alpha(1).start();


    }
    private void hide() {
        ConstraintLayout view = rootView.findViewById(R.id.menuView);
        ValueAnimator anim = ValueAnimator.ofInt(view.getMeasuredHeight(), Unit.dpToPixel(64, getContext()));
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int val = (Integer) valueAnimator.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                layoutParams.height = val;
                view.setLayoutParams(layoutParams);
            }

        });
        anim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                hideFragment();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        anim.setDuration(200);
        anim.start();
        rootView.findViewById(R.id.shadow).animate().alpha(0).start();
    }

    void hideFragment() {
        FragmentManager fragmentManager = getParentFragmentManager();
        fragmentManager.beginTransaction().hide(this).commitAllowingStateLoss();
    }

    @Override
    public void menuButtonTapped(MenuButton button) {
        switch (button) {
            case profile:
                router.toProfile();
                break;
            case products:
                router.toProducts();
                break;
        }

        hide();
    }
}