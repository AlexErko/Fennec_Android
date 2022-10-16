package ua.com.fennec;

import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import ua.com.fennec.preAuthFlow.PreAuthCoordinator;
import ua.com.fennec.services.loading.LoadingService;
import ua.com.fennec.services.message.MessageService;

public class MainActivity extends AppCompatActivity implements MainActivityRouter {


    Fragment current;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            setTheme(R.style.Theme_Feneec_Dark);
        } else {
            setTheme(R.style.Theme_Feneec_Light);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LoadingService.setIndicator(findViewById(R.id.indicatorView));
        FrameLayout messageLayout = findViewById(R.id.messageLayout);
        MessageService.setView(messageLayout);
        toPreAuthCoordinator();
    }

    @Override
    public void toPreAuthCoordinator() {
        current = new PreAuthCoordinator(this);
        showCurrentFragment();
    }

    @Override
    public void toAfterAuthCoordinator() {

    }


    private void showCurrentFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);
        ft.replace(R.id.fragmentContainer, current).commitAllowingStateLoss();
    }

}


