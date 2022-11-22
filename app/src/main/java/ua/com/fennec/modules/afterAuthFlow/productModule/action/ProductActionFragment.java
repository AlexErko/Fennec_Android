package ua.com.fennec.modules.afterAuthFlow.productModule.action;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ua.com.fennec.R;
import ua.com.fennec.customs.FennecBottomFragment;
import ua.com.fennec.customs.swipeGesture.OnSwipeTouchListener;

public class ProductActionFragment extends FennecBottomFragment {


    View rootView;

    public ProductActionFragment() {

    }

    @Override
    public void onResume() {
        super.onResume();
        show(rootView);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_product_action, container, false);
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