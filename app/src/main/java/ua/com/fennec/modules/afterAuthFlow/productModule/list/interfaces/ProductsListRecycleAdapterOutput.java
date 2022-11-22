package ua.com.fennec.modules.afterAuthFlow.productModule.list.interfaces;


import ua.com.fennec.models.Product;

public interface ProductsListRecycleAdapterOutput {
    void showMoreTapped();
    void productTapped(Product product);
}