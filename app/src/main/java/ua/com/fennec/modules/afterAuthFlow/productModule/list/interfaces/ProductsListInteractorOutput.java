package ua.com.fennec.modules.afterAuthFlow.productModule.list.interfaces;

import java.util.ArrayList;

import ua.com.fennec.models.Product;

public interface ProductsListInteractorOutput {
    void productsGot(ArrayList<Product> products, Integer total);
}
