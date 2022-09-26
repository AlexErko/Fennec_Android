package ua.com.fennec.preAuthFlow.loginModule;

import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Timer;

import ua.com.fennec.R;
import ua.com.fennec.preAuthFlow.PreAuthRouter;

public class LoginFragment extends Fragment {


    private PreAuthRouter router;
    private View rootView;
    public LoginFragment(PreAuthRouter router) {
        this.router = router;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


//        Timer t = new java.util.Timer();
//        t.schedule(
//                new java.util.TimerTask() {
//                    @Override
//                    public void run() {
//                        Runnable task = new Runnable() {
//                            public void run() {
//
//                            }
//                        };
//                        task.run();
//                        t.cancel();
//                    }
//                },
//                5000
//        );


    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("###", "ASDASDASDADSADS");
        rootView = inflater.inflate(R.layout.fragment_login, container, false);
        rootView.findViewById(R.id.googleButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                router.showCode("");
            }
        });
        return rootView;
    }
}