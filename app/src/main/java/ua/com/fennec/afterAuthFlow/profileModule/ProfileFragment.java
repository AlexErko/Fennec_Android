package ua.com.fennec.afterAuthFlow.profileModule;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import ua.com.fennec.Constants;
import ua.com.fennec.MainActivity;
import ua.com.fennec.R;
import ua.com.fennec.afterAuthFlow.AfterAuthRouter;
import ua.com.fennec.afterAuthFlow.profileModule.interfaces.ProfileInteractorOutput;
import ua.com.fennec.customs.ui.confirmButton.ConfirmButton;
import ua.com.fennec.globalModules.getPhotoModule.GetPhotoFragment;
import ua.com.fennec.models.Profile;
import ua.com.fennec.preAuthFlow.PreAuthRouter;
import ua.com.fennec.services.KeyboardService;
import ua.com.fennec.services.api.DataPart;
import ua.com.fennec.services.api.VolleyMultipartRequest;
import ua.com.fennec.services.api.bodyModels.UpdateProfileBody;
import ua.com.fennec.services.message.MessageService;
import ua.com.fennec.services.storage.StorageService;
import ua.com.fennec.services.string.StringService;

public class ProfileFragment extends Fragment implements ProfileInteractorOutput{


    private AfterAuthRouter router;
    private View rootView;
    private ProfileInteractor interactor;

    private Profile profile = null;
    private TextWatcher textWatcher = null;
    public ProfileFragment(AfterAuthRouter router) {
        this.router = router;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        interactor = new ProfileInteractor(this, getContext());
        initTextWatcher();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Profile profile = StorageService.shared.getProfile();
        if (profile != null) {
            this.profile = profile;
            configView(profile);
        }
        interactor.getProfile();
    }

    private void initTextWatcher() {
        textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                textChanged();
            }
        };
    }
    private void configView(Profile profile) {
        if (StringService.isNull(profile.user_name) == false) {
            ((EditText) rootView.findViewById(R.id.nameEditText)).setText(profile.user_name);
        }
        if (StringService.isNull(profile.user_email) == false) {
            ((EditText) rootView.findViewById(R.id.emailEditText)).setText(profile.user_email);
        }
        if (StringService.isNull(profile.user_phone) == false) {
            ((EditText) rootView.findViewById(R.id.phoneEditText)).setText(profile.user_phone);
        }
        if (StringService.isNull(profile.company_name) == false) {
            ((EditText) rootView.findViewById(R.id.companyEditText)).setText(profile.company_name);
        }
        ((ConfirmButton) rootView.findViewById(R.id.saveButton)).setActivated(getContext(), false);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        EditText nameEditText = ((EditText) rootView.findViewById(R.id.nameEditText));
        nameEditText.addTextChangedListener(textWatcher);
        EditText emailEditText = ((EditText) rootView.findViewById(R.id.emailEditText));
        emailEditText.addTextChangedListener(textWatcher);
        EditText companyEditText = ((EditText) rootView.findViewById(R.id.companyEditText));
        companyEditText.addTextChangedListener(textWatcher);
        rootView.findViewById(R.id.saveButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((ConfirmButton) rootView.findViewById(R.id.saveButton)).isActivated()) {
                    KeyboardService.hideKeyboard(getActivity());
                    interactor.updateProfile(new UpdateProfileBody(profile.user_phone, companyEditText.getText().toString(), nameEditText.getText().toString(), emailEditText.getText().toString()));
                }
            }
        });
        rootView.findViewById(R.id.userImageView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetPhotoFragment.showFragment((AppCompatActivity) getActivity());
            }
        });
        return rootView;
    }

    public void textChanged() {
        Boolean buttonIsActive = true;
        if (profile != null) {
            String newName = ((EditText) rootView.findViewById(R.id.nameEditText)).getText().toString();
            if (newName.length() > 2 && buttonIsActive == true) {
                buttonIsActive = true;
            } else {
                buttonIsActive = false;
            }
            String newEmail = ((EditText) rootView.findViewById(R.id.emailEditText)).getText().toString();
            if (newEmail.contains("@") && buttonIsActive == true) {
                buttonIsActive = true;
            } else {
                buttonIsActive = false;
            }
            String newCompanyName = ((EditText) rootView.findViewById(R.id.companyEditText)).getText().toString();
            if (newCompanyName.length() > 2 && buttonIsActive == true) {
                buttonIsActive = true;
            } else {
                buttonIsActive = false;
            }
        } else {
            buttonIsActive = false;
        }
        ((ConfirmButton) rootView.findViewById(R.id.saveButton)).setActivated(getContext(), buttonIsActive);
    }
    @Override
    public void profileDidGot(Profile profile) {
        StorageService.shared.setProfile(profile);
        this.profile = profile;
        configView(profile);
    }

    @Override
    public void profileDidUpdated(Profile profile) {
        MessageService.showMessage(getContext().getString(R.string.profile_updated), MessageService.Type.success, getContext());
        profileDidGot(profile);
    }
}