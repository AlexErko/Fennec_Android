package ua.com.fennec.modules.afterAuthFlow.productModule;

import ua.com.fennec.models.Product;

public interface ProductRouter {
    public void toList();
    public void showProductAction(Product product);
    public void toAddProduct();
    public void toEditProduct(Product product);
    public void showMenu();
}
