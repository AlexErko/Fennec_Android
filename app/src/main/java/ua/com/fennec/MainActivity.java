package ua.com.fennec;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import ua.com.fennec.preAuthFlow.PreAuthCoordinator;

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


