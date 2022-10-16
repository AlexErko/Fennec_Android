package ua.com.fennec.preAuthFlow.codeModule;

import android.content.Context;
import android.os.Bundle;

import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
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
import ua.com.fennec.preAuthFlow.codeModule.interfaces.CodeInteractorOutput;
import ua.com.fennec.preAuthFlow.loginModule.LoginInteractor;
import ua.com.fennec.services.KeyboardService;
import ua.com.fennec.services.message.MessageService;


public class CodeFragment extends FennecBottomFragment implements CodeInteractorOutput {


    View rootView;

    private PreAuthRouter router;
    private CodeInteractor interactor;
    private String userPhone;

    public CodeFragment(PreAuthRouter router, String phone) {
        this.router = router;
        this.userPhone = phone;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.interactor = new CodeInteractor(this, getContext());


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
            }
        });

        ((EditText) rootView.findViewById(R.id.editText1)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String code = s.toString();
                if (code.length() == 3) {
                    KeyboardService.hideKeyboard(getActivity());
                    interactor.confirmAuthPhone(userPhone, code);
                }
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

    @Override
    public void phoneDidConfirmed(String phone, String token) {
        MessageService.showMessage(getContext().getString(R.string.phone_confirmed), MessageService.Type.success, getContext());
    }
}