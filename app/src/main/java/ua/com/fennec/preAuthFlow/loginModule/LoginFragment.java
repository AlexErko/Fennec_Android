package ua.com.fennec.preAuthFlow.loginModule;

import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Timer;

import ua.com.fennec.R;
import ua.com.fennec.customs.ui.FennecEditText;
import ua.com.fennec.customs.ui.confirmButton.ConfirmButton;
import ua.com.fennec.preAuthFlow.PreAuthRouter;
import ua.com.fennec.preAuthFlow.loginModule.interfaces.LoginInteractorOutput;
import ua.com.fennec.services.KeyboardService;
import ua.com.fennec.services.loading.LoadingService;
import ua.com.fennec.services.message.MessageService;

public class LoginFragment extends Fragment implements LoginInteractorOutput {


    private PreAuthRouter router;
    private View rootView;
    boolean allowMask = true;
    private LoginInteractor interactor;

    public LoginFragment(PreAuthRouter router) {
        this.router = router;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        interactor = new LoginInteractor(this, getContext());
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_login, container, false);

        ((EditText) rootView.findViewById(R.id.editText1)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                textChanged();
            }
        });
        ConfirmButton confirmButton = rootView.findViewById(R.id.confirmButton);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (confirmButton.isActivated() == true) {
                    KeyboardService.hideKeyboard(getActivity());
                    FennecEditText logEdit = rootView.findViewById(R.id.editText1);
//                    logEdit.setEditable(getContext(), false);
                    interactor.authPhone(logEdit.getText().toString());
                }
            }
        });
        return rootView;
    }


    void textChanged(){
        EditText logEdit = rootView.findViewById(R.id.editText1);

        if (allowMask == false){
            ConfirmButton button = rootView.findViewById(R.id.confirmButton);
            if (logEdit.getText().toString().length() == 19) {
                button.setActivated(getContext(), true);
            } else {
                if (button.isActivated() != false) {
                    button.setActivated(getContext(), false);
                }
            }
            allowMask = true;
            return;
        }

        if (logEdit.getText().length() < 1){
            return;

        }
        char[] textChars = logEdit.getText().toString().replace("+38", "").toCharArray();
        if (textChars[textChars.length - 1] == ' ' || textChars[textChars.length - 1] == '-'){
            return;
        }

        ArrayList<String> ints = new ArrayList<>();
        ArrayList<String> intFromField = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            ints.add(i + "");
        }

        for (int i = 0; i < textChars.length; i++){
            for (int y = 0; y < ints.size(); y++){
                if ((textChars[i] + "").equals(ints.get(y))){
                    intFromField.add(ints.get(y));
                }

            }
        }

        char[] maskChars = "+38 (XXX) XXX-XX-XX".toCharArray();
        int indexInt = 0;
        String returnedString = "";


        for (int i = 0; i < maskChars.length; i++) {
            if (indexInt >= intFromField.size()){
                break;
            }
            if (maskChars[i] == 'X'){
                returnedString += intFromField.get(indexInt);
                indexInt += 1;
            }else{

                returnedString += maskChars[i];
            }
        }
        allowMask = false;
        logEdit.setText(returnedString);
        logEdit.setSelection(returnedString.length());
    }
    @Override
    public void codeDidSent(String phone) {
        router.showCode(phone);
    }
}