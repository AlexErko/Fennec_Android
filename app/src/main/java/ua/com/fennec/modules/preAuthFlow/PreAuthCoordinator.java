package ua.com.fennec.modules.preAuthFlow;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ua.com.fennec.MainActivityRouter;
import ua.com.fennec.R;
import ua.com.fennec.customs.FennecFragment;
import ua.com.fennec.modules.preAuthFlow.codeModule.CodeFragment;
import ua.com.fennec.modules.preAuthFlow.loginModule.LoginFragment;
import ua.com.fennec.services.storage.StorageService;

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
    public void toProfile(String token) {
        StorageService.shared.setToken(token);
        router.toAfterAuthCoordinator();
    }

    @Override
    public void showCode(String phone) {
        addFragment(new CodeFragment(this, phone),false);
    }
}