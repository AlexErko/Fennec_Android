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
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
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
import ua.com.fennec.preAuthFlow.PreAuthRouter;
import ua.com.fennec.services.KeyboardService;
import ua.com.fennec.services.api.DataPart;
import ua.com.fennec.services.api.VolleyMultipartRequest;
import ua.com.fennec.services.api.bodyModels.UpdateProfileBody;
import ua.com.fennec.services.storage.StorageService;

public class ProfileFragment extends Fragment implements ProfileInteractorOutput{


    private AfterAuthRouter router;
    private View rootView;
    private ProfileInteractor interactor;


    private static final int REQUEST_PERMISSIONS = 100;
    private Bitmap bitmap;
    private String filePath;


    public ProfileFragment(AfterAuthRouter router) {
        this.router = router;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        interactor = new ProfileInteractor(this, getContext());
        interactor.getProfile();
        interactor.updateProfile(new UpdateProfileBody("380673223276", "CAFsdaE", "Alasex", "losassha.asd@asda.aas"));
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        rootView.findViewById(R.id.imageView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestPermissions();
            }
        });
        return rootView;
    }
    private void requestPermissions(){
        if ((ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) && (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
            if ((ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) && (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.READ_EXTERNAL_STORAGE))) {

            } else {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_PERMISSIONS);
            }
        } else {
            showFileChooser();
        }

    }
    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);

        someActivityResultLauncher.launch(Intent.createChooser(intent, "Select Picture"));
    }



    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null && result.getData().getData() != null) {
                        Uri picUri = result.getData().getData();
                        filePath = getPath(picUri);
                        if (filePath != null) {
                            try {

                                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), picUri);
                                interactor.uploadCompanyLogo(bitmap);
                                ((ImageView) rootView.findViewById(R.id.imageView)).setImageBitmap(bitmap);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(
                                    getContext(),"no image selected",
                                    Toast.LENGTH_LONG).show();
                        }
                    }

                }
            });



    public String getPath(Uri uri) {
        Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();

        cursor = getActivity().getContentResolver().query(
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        cursor.moveToFirst();
        @SuppressLint("Range") String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();

        return path;
    }

}