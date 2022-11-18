package ua.com.fennec.afterAuthFlow;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;

import ua.com.fennec.Constants;
import ua.com.fennec.MainActivityRouter;
import ua.com.fennec.R;
import ua.com.fennec.afterAuthFlow.menuModule.MenuFragment;
import ua.com.fennec.afterAuthFlow.profileModule.ProfileFragment;
import ua.com.fennec.customs.FennecFragment;

public class AfterAuthCoordinator extends FennecFragment implements AfterAuthRouter {


    View rootView;
    MainActivityRouter router;
    private MenuFragment menuFragment = new MenuFragment();
    public AfterAuthCoordinator(MainActivityRouter router) {
        this.router = router;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_after_auth_coordinator, container, false);
        return rootView;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
        toProfile();
        initMenu();
    }

    private void initMenu() {
//        View r = rootView.findViewById(R.id.menuContainer);
//        r.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.orange));
//        Log.d(Constants.TAG, r.toString());
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.menuContainer, menuFragment).commitAllowingStateLoss();
//
//        ft.hide(menuFragment).commitAllowingStateLoss();
    }

    @Override
    public void toProfile() {
        showFragment(new ProfileFragment(this), false);
    }

    @Override
    public void toLogin() {

    }

    @Override
    public void showMenu() {
        menuFragment.show();
    }
}