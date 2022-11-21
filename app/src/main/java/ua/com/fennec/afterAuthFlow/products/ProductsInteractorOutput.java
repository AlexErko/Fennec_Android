package ua.com.fennec.afterAuthFlow.products;

import androidx.annotation.NonNull;

import java.util.ArrayList;

import ua.com.fennec.models.Product;
import ua.com.fennec.models.Profile;

public interface ProductsInteractorOutput {
    void productsGot(ArrayList<Product> products);
}
