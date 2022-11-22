package ua.com.fennec.modules.afterAuthFlow;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentTransaction;

import ua.com.fennec.MainActivityRouter;
import ua.com.fennec.R;
import ua.com.fennec.modules.afterAuthFlow.menuModule.MenuFragment;
import ua.com.fennec.modules.afterAuthFlow.productModule.ProductCoordinator;
import ua.com.fennec.modules.afterAuthFlow.profileModule.ProfileFragment;
import ua.com.fennec.customs.FennecFragment;

public class AfterAuthCoordinator extends FennecFragment implements AfterAuthRouter {


    View rootView;
    MainActivityRouter router;
    private MenuFragment menuFragment = new MenuFragment(this);
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
        this.init();
        toProfile();
        initMenu();
    }

    private void initMenu() {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.menuContainer, menuFragment).commitAllowingStateLoss();
    }

    @Override
    public void toProfile() {
        showFragment(new ProfileFragment(this), false);
    }

    @Override
    public void toLogin() {

    }

    @Override
    public void toProducts() {
        showFragment(new ProductCoordinator(this), false);
    }

    @Override
    public void showMenu() {
        menuFragment.show();
    }

}