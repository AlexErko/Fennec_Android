package ua.com.fennec.afterAuthFlow;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import ua.com.fennec.MainActivityRouter;
import ua.com.fennec.R;
import ua.com.fennec.afterAuthFlow.profileModule.ProfileFragment;
import ua.com.fennec.customs.FennecFragment;
import ua.com.fennec.preAuthFlow.PreAuthRouter;
import ua.com.fennec.preAuthFlow.codeModule.CodeFragment;
import ua.com.fennec.preAuthFlow.loginModule.LoginFragment;
import ua.com.fennec.services.storage.StorageService;

public class AfterAuthCoordinator extends FennecFragment implements AfterAuthRouter {


    
    MainActivityRouter router;
    public AfterAuthCoordinator(MainActivityRouter router) {
        this.router = router;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_after_auth_coordinator, container, false);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        init();
        toProfile();
    }


    @Override
    public void toProfile() {
        showFragment(new ProfileFragment(this), false);
    }

    @Override
    public void toLogin() {

    }
}