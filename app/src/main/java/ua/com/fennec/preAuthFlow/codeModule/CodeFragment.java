package ua.com.fennec.preAuthFlow.codeModule;

import android.content.Context;
import android.os.Bundle;

import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import ua.com.fennec.R;
import ua.com.fennec.customs.FennecBottomFragment;
import ua.com.fennec.preAuthFlow.PreAuthRouter;
import ua.com.fennec.customs.swipeGesture.OnSwipeTouchListener;


public class CodeFragment extends FennecBottomFragment {


    View rootView;

    private PreAuthRouter router;
    public CodeFragment(PreAuthRouter router) {
        this.router = router;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }


    @Override
    public void onResume() {
        super.onResume();
        show(rootView);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_code, container, false);
        rootView.findViewById(R.id.bottomView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("###", "asda");
            }
        });


        rootView.findViewById(R.id.bottomView).setOnTouchListener(new OnSwipeTouchListener() {
            public boolean onSwipeBottom() {
                    hide(rootView);
                return true;
            }
        });
        rootView.findViewById(R.id.shadow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hide(rootView);
            }
        });
        return rootView;
    }
}