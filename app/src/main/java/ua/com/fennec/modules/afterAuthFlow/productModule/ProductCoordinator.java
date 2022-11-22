package ua.com.fennec.modules.afterAuthFlow.productModule;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ua.com.fennec.Constants;
import ua.com.fennec.R;
import ua.com.fennec.modules.afterAuthFlow.AfterAuthRouter;
import ua.com.fennec.modules.afterAuthFlow.productModule.action.ProductActionFragment;
import ua.com.fennec.modules.afterAuthFlow.productModule.list.ProductsListFragment;
import ua.com.fennec.customs.FennecFragment;
import ua.com.fennec.models.Product;

public class ProductCoordinator extends FennecFragment implements ProductRouter{


    private AfterAuthRouter router;
    private View rootView;
    public ProductCoordinator(AfterAuthRouter router) {
        this.router = router;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.init();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_product_coordinator, container, false);


        return rootView;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toList();

    }


    @Override
    public void toList() {
        showFragment(new ProductsListFragment(this), true);
    }

    @Override
    public void showProductAction(Product product) {
        addFragment(new ProductActionFragment(), false);
    }

    @Override
    public void toAddProduct() {

    }

    @Override
    public void toEditProduct(Product product) {
    }

    @Override
    public void showMenu() {
        router.showMenu();
    }
}