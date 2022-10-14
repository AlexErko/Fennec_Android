package ua.com.fennec.preAuthFlow;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ua.com.fennec.MainActivityRouter;
import ua.com.fennec.R;
import ua.com.fennec.customs.FennecFragment;
import ua.com.fennec.preAuthFlow.codeModule.CodeFragment;
import ua.com.fennec.preAuthFlow.loginModule.LoginFragment;

public class PreAuthCoordinator extends FennecFragment implements PreAuthRouter{


    
    MainActivityRouter router;
    public PreAuthCoordinator(MainActivityRouter router) {
        this.router = router;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pre_auth_coordinator, container, false);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        init();
        toLogin();
    }

    private void toLogin() {
        LoginFragment loginFragment = new LoginFragment(this);
        showFragment(loginFragment, false);
    }


    @Override
    public void toProfile(String user) {

    }

    @Override
    public void showCode(String phone) {
        addFragment(new CodeFragment(this, phone),false);
    }
}