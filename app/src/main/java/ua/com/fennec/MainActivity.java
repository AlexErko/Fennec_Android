package ua.com.fennec;

import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import ua.com.fennec.modules.afterAuthFlow.AfterAuthCoordinator;
import ua.com.fennec.modules.preAuthFlow.PreAuthCoordinator;
import ua.com.fennec.services.loading.LoadingService;
import ua.com.fennec.services.message.MessageService;
import ua.com.fennec.services.storage.StorageService;

public class MainActivity extends AppCompatActivity implements MainActivityRouter {


    Fragment current;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_Feneec_Light);
        super.onCreate(savedInstanceState);
        StorageService.initShared(this);
        setContentView(R.layout.activity_main);
        LoadingService.setIndicator(findViewById(R.id.indicatorView));
        FrameLayout messageLayout = findViewById(R.id.messageLayout);
        MessageService.setView(messageLayout);
        if (StorageService.shared.getToken() == "") {
            toPreAuthCoordinator();
        } else {
            toAfterAuthCoordinator();
        }



    }

    @Override
    public void toPreAuthCoordinator() {
        current = new PreAuthCoordinator(this);
        showCurrentFragment();
    }

    @Override
    public void toAfterAuthCoordinator() {
        current = new AfterAuthCoordinator(this);
        showCurrentFragment();
    }


    private void showCurrentFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);
        ft.replace(R.id.fragmentContainer, current).commitAllowingStateLoss();
    }

}


