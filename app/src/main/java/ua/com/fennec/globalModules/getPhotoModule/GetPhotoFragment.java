package ua.com.fennec.globalModules.getPhotoModule;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.dhaval2404.imagepicker.ImagePicker;

import java.util.ArrayList;

import ua.com.fennec.Constants;
import ua.com.fennec.R;
import ua.com.fennec.customs.FennecBottomFragment;
import ua.com.fennec.customs.swipeGesture.OnSwipeTouchListener;
import ua.com.fennec.customs.ui.sectionSwitcher.OnSectionChangedListener;
import ua.com.fennec.customs.ui.sectionSwitcher.SectionSwitcher;
import ua.com.fennec.globalModules.getPhotoModule.adapters.GetPhotoRecycleAdapter;
import ua.com.fennec.globalModules.getPhotoModule.interfaces.GetPhotoInteractorOutput;
import ua.com.fennec.preAuthFlow.PreAuthRouter;
import ua.com.fennec.preAuthFlow.loginModule.LoginFragment;
import ua.com.fennec.services.KeyboardService;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

final public class GetPhotoFragment extends Fragment implements GetPhotoInteractorOutput {

    private View rootView;
    private GetPhotoInteractor interactor;
    private GetPhotoRecycleAdapter publicAdapter = null;
    private GetPhotoRecycleAdapter privateAdapter = null;
    private Boolean isPrivateSection = true;
    public static GetPhotoFragment showFragment(AppCompatActivity activity) {
        int layoutId = View.generateViewId();
        final ViewGroup viewGroup = (ViewGroup) ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0);
        FrameLayout layout = new FrameLayout(activity);
        layout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        layout.setClickable(true);
        layout.setId(layoutId);
        viewGroup.addView(layout);


        GetPhotoFragment photoFragment = new GetPhotoFragment();

        FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);
        ft.add(layoutId, photoFragment).commitAllowingStateLoss();




        return photoFragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        interactor = new GetPhotoInteractor(getContext(), this);
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
                ImagePicker.with(getActivity())
                        .crop()	    			//Crop image(Optional), Check Customization for more option
                        .compress(1024)			//Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start();
            }
        });
        return rootView;
    }
    ActivityResultLauncher<Intent> launcher=
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),(ActivityResult result)->{
                if(result.getResultCode()==RESULT_OK){
                    Uri uri=result.getData().getData();
                    // Use the uri to load the image
                }else if(result.getResultCode()== RESULT_CANCELED){
                    // Use ImagePicker.Companion.getError(result.getData()) to show an error
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
        privateAdapter = new GetPhotoRecycleAdapter(getContext(), photos);
        if (isPrivateSection == true) {
            recyclerView.setAdapter(privateAdapter);
        }
    }
    @Override
    public void publicGalleryGot(ArrayList<String> photos) {
        RecyclerView recyclerView = rootView.findViewById(R.id.recyclerView);
        publicAdapter = new GetPhotoRecycleAdapter(getContext(), photos);
        if (isPrivateSection == false) {
            recyclerView.setAdapter(publicAdapter);
        }
    }
}
