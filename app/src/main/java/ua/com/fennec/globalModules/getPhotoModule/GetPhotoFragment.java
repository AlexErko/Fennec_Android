package ua.com.fennec.globalModules.getPhotoModule;

import android.Manifest;
import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.BitmapCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import pub.devrel.easypermissions.EasyPermissions;
import ua.com.fennec.Constants;
import ua.com.fennec.R;
import ua.com.fennec.customs.swipeGesture.OnSwipeTouchListener;
import ua.com.fennec.customs.ui.sectionSwitcher.OnSectionChangedListener;
import ua.com.fennec.customs.ui.sectionSwitcher.SectionSwitcher;
import ua.com.fennec.globalModules.getPhotoModule.adapters.GetPhotoRecycleAdapter;
import ua.com.fennec.globalModules.getPhotoModule.interfaces.GetPhotoInteractorOutput;
import ua.com.fennec.globalModules.getPhotoModule.interfaces.GetPhotoOutput;
import ua.com.fennec.globalModules.getPhotoModule.interfaces.GetPhotoRecycleAdapterOutput;
import ua.com.fennec.services.KeyboardService;

final public class GetPhotoFragment extends Fragment implements GetPhotoInteractorOutput {

    private View rootView;
    private GetPhotoInteractor interactor;
    private GetPhotoRecycleAdapter publicAdapter = null;
    private GetPhotoRecycleAdapter privateAdapter = null;
    private Boolean isPrivateSection = true;
    private GetPhotoRecycleAdapterOutput adapterOutput;
    private GetPhotoOutput output;
    ArrayList<Uri> list = new ArrayList<>();
    public static GetPhotoFragment showFragment(AppCompatActivity activity, GetPhotoOutput output) {
        int layoutId = View.generateViewId();
        final ViewGroup viewGroup = (ViewGroup) ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0);
        FrameLayout layout = new FrameLayout(activity);
        layout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        layout.setClickable(true);
        layout.setId(layoutId);
        Log.d(Constants.TAG, viewGroup.getChildCount() + " asdas");
        viewGroup.addView(layout, 1);


        GetPhotoFragment photoFragment = new GetPhotoFragment(output);
        FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);
        ft.add(layoutId, photoFragment).commitAllowingStateLoss();

        return photoFragment;
    }

    private GetPhotoFragment(GetPhotoOutput output) {
        this.output = output;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        interactor = new GetPhotoInteractor(getContext(), this);
        adapterOutput = new GetPhotoRecycleAdapterOutput() {
            @Override
            public void deleteTapped(String filepath) {
                interactor.deleteImage(filepath);
            }
            @Override
            public void imageTapped(String filepath) {
                imageSelected(filepath);
            }
        };
    }

    private void imageSelected(String filepath) {
        output.photoDidGot(filepath);
        hide();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        show();
        interactor.getPrivateGallery();
        interactor.getPublicGallery();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_get_photo, container, false);

        rootView.findViewById(R.id.bottomView).setOnTouchListener(new OnSwipeTouchListener() {
            public boolean onSwipeBottom() {
                hide();
                return true;
            }
        });
        rootView.findViewById(R.id.bottomView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        rootView.findViewById(R.id.shadow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hide();
            }
        });
        RecyclerView recyclerView = rootView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 4));

        ((SectionSwitcher) rootView.findViewById(R.id.sectionSwitcher)).setOnSectionChangeListener(new OnSectionChangedListener() {
            @Override
            public void sectionChanged(Boolean isFirstSelected) {
                isPrivateSection = isFirstSelected;
                if (publicAdapter != null) {
                    if (isPrivateSection == false) {
                        recyclerView.setAdapter(publicAdapter);
                    }
                }
                if (privateAdapter != null) {
                    if (isPrivateSection == true) {
                        recyclerView.setAdapter(privateAdapter);
                    }
                }
                rootView.findViewById(R.id.addImageButton).setVisibility(isFirstSelected ? View.VISIBLE : View.GONE);
            }
        });

        rootView.findViewById(R.id.addImageButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] strings = {Manifest.permission.READ_EXTERNAL_STORAGE};
                if (EasyPermissions.hasPermissions(getContext(), strings)) {
                    Intent i = new Intent(Intent.ACTION_PICK);
                    i.setType("image/*");
                    someActivityResultLauncher.launch(i);
                } else {
                    Log.d(Constants.TAG, "Request");
                    EasyPermissions.requestPermissions(getActivity() ,"App nee", 100, strings);
                }
            }
        });
        return rootView;
    }
    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Uri uri= result.getData().getData();
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver() , uri);
                            Log.d(Constants.TAG, BitmapCompat.getAllocationByteCount(bitmap) + " BYTES");
                            float aspectRatio = bitmap.getWidth() /
                                    (float) bitmap.getHeight();
                            int height = 380;
                            int width = Math.round(height * aspectRatio);
                            bitmap = Bitmap.createScaledBitmap(
                                    bitmap, width, height, false);
                            Log.d(Constants.TAG, BitmapCompat.getAllocationByteCount(bitmap) + " BYTES");
                            interactor.uploadImage(bitmap);
                        } catch (Exception e)
                        {
                        }

                    }
                }
            });


    private void show() {
        LinearLayout view = rootView.findViewById(R.id.bottomView);
        KeyboardService.hideKeyboard(getActivity());

        view.post(new Runnable() {
            @Override
            public void run() {
                int height = view.getHeight();
                rootView.findViewById(R.id.shadow).animate().alpha(1).start();
                ObjectAnimator.ofFloat(view,"translationY",-height).setDuration(300).start();
            }
        });
    }
    private void dismiss() {
        getParentFragmentManager().beginTransaction().remove(this).commitAllowingStateLoss();
    }
    private void hide() {
        LinearLayout view = rootView.findViewById(R.id.bottomView);

        KeyboardService.hideKeyboard(getActivity());
        view.post(new Runnable() {
            @Override
            public void run() {
                int height = view.getHeight();
                rootView.findViewById(R.id.shadow).animate().alpha(0).start();
                ObjectAnimator objectAnimator  = ObjectAnimator.ofFloat(view,"translationY",height).setDuration(300);
                objectAnimator.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        if (this != null) {
                            if (rootView != null) {
                                rootView.setVisibility(View.GONE);
                                ViewParent parent = rootView.getParent();
                                if (parent != null) {
                                    ((ViewGroup) parent.getParent()).removeView((View) rootView.getParent());
                                    dismiss();
                                }
                            }
                        }
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
                objectAnimator.start();
            }
        });
    }

    @Override
    public void privateGalleryGot(ArrayList<String> photos) {
        RecyclerView recyclerView = rootView.findViewById(R.id.recyclerView);
        privateAdapter = new GetPhotoRecycleAdapter(getContext(), photos, adapterOutput, false);
        if (isPrivateSection == true) {
            recyclerView.setAdapter(privateAdapter);
        }
    }
    @Override
    public void publicGalleryGot(ArrayList<String> photos) {
        RecyclerView recyclerView = rootView.findViewById(R.id.recyclerView);
        publicAdapter = new GetPhotoRecycleAdapter(getContext(), photos, adapterOutput, true);
        if (isPrivateSection == false) {
            recyclerView.setAdapter(publicAdapter);
        }
    }

    @Override
    public void imageAdded(String path) {
        imageSelected(path);
    }
}
