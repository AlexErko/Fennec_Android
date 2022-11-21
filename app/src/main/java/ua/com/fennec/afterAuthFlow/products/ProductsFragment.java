package ua.com.fennec.afterAuthFlow.products;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import ua.com.fennec.Constants;
import ua.com.fennec.R;
import ua.com.fennec.afterAuthFlow.AfterAuthRouter;
import ua.com.fennec.afterAuthFlow.profileModule.interfaces.ProfileInteractorOutput;
import ua.com.fennec.customs.ui.confirmButton.ConfirmButton;
import ua.com.fennec.globalModules.getPhotoModule.GetPhotoFragment;
import ua.com.fennec.globalModules.getPhotoModule.interfaces.GetPhotoOutput;
import ua.com.fennec.models.Product;
import ua.com.fennec.models.Profile;
import ua.com.fennec.services.KeyboardService;
import ua.com.fennec.services.api.bodyModels.UpdateProfileBody;
import ua.com.fennec.services.message.MessageService;
import ua.com.fennec.services.storage.StorageService;
import ua.com.fennec.services.string.StringService;

public class ProductsFragment extends Fragment implements ProductsInteractorOutput {


    private AfterAuthRouter router;
    private View rootView;
    private ProductsInteractor interactor;
    private RecyclerView recyclerView;
    private ProductsRecycleAdapter adapter;
    public ProductsFragment(AfterAuthRouter router) {
        this.router = router;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        interactor = new ProductsInteractor(this, getContext());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        interactor.getProducts();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_products, container, false);
        rootView.findViewById(R.id.menuButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                router.showMenu();
            }
        });
        recyclerView = rootView.findViewById(R.id.recyclerView);
        GridLayoutManager manager = new GridLayoutManager(getContext(), 2, RecyclerView.VERTICAL, false);


        recyclerView.setLayoutManager(manager);
        return rootView;
    }


    @Override
    public void productsGot(ArrayList<Product> products) {
        adapter = new ProductsRecycleAdapter(getContext(), products);
        recyclerView.setAdapter(adapter);
        GridLayoutManager manager = (GridLayoutManager) recyclerView.getLayoutManager();
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                switch(adapter.getItemViewType(position)) {
                    case ProductsRecycleAdapter.FOOTER_VIEW:
                        return 2;
                    default:
                        return 1;
                }
            }
        });
    }
}