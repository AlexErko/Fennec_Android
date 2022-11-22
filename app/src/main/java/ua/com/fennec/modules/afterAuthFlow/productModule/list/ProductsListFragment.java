package ua.com.fennec.modules.afterAuthFlow.productModule.list;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ua.com.fennec.Constants;
import ua.com.fennec.R;
import ua.com.fennec.modules.afterAuthFlow.productModule.ProductRouter;
import ua.com.fennec.modules.afterAuthFlow.productModule.list.interfaces.ProductsListInteractorOutput;
import ua.com.fennec.modules.afterAuthFlow.productModule.list.interfaces.ProductsListRecycleAdapterOutput;
import ua.com.fennec.models.Product;

public class ProductsListFragment extends Fragment implements ProductsListInteractorOutput, ProductsListRecycleAdapterOutput {


    private ProductRouter router;
    private View rootView;
    private ProductsListInteractor interactor;
    private RecyclerView recyclerView;
    private ProductsListRecycleAdapter adapter;
    private ArrayList<Product> productsArrayList = new ArrayList<>();
    private Integer page = 1;
    public ProductsListFragment(ProductRouter router) {
        this.router = router;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        interactor = new ProductsListInteractor(this, getContext());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        interactor.getProducts(page);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_products_list, container, false);
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
    public void productsGot(ArrayList<Product> products, Integer total) {

        for (int i = 0; i < products.size(); i++) {
            productsArrayList.add(products.get(i));
        }

        adapter = new ProductsListRecycleAdapter(getContext(), productsArrayList,this, total > productsArrayList.size());
        recyclerView.setAdapter(adapter);
        GridLayoutManager manager = (GridLayoutManager) recyclerView.getLayoutManager();
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                switch(adapter.getItemViewType(position)) {
                    case ProductsListRecycleAdapter.FOOTER_VIEW:
                        return 2;
                    default:
                        return 1;
                }
            }
        });
    }

    @Override
    public void showMoreTapped() {
        page += 1;
        interactor.getProducts(page);
    }

    @Override
    public void productTapped(Product product) {
        Log.d(Constants.TAG, "PRODUCTTAPPED(");
        router.showProductAction(product);
    }
}