package ua.com.fennec.modules.afterAuthFlow.productModule.list;

import android.content.Context;

import androidx.annotation.Nullable;

import ua.com.fennec.R;
import ua.com.fennec.modules.afterAuthFlow.productModule.list.interfaces.ProductsListInteractorOutput;
import ua.com.fennec.services.api.ApiService;
import ua.com.fennec.services.api.ApiServiceOutput;
import ua.com.fennec.services.api.responseModels.ApiGetProductResponse;
import ua.com.fennec.services.loading.LoadingService;
import ua.com.fennec.services.message.MessageService;

public class ProductsListInteractor {


    private ProductsListInteractorOutput output;
    private ApiService apiService;
    private Context context;

    ProductsListInteractor(ProductsListInteractorOutput output, Context context) {
      this.output = output;
      this.context = context;
      this.apiService = new ApiService(context);
    };




    void getProducts(Integer page) {
        LoadingService.start();
        apiService.getProducts(page, new ApiServiceOutput<ApiGetProductResponse>() {
            @Nullable
            @Override
            public void onResponse(ApiGetProductResponse response) {
                if (response != null) {
                    if (response.answer.status == 0) {
                        if (response.products != null) {
                            output.productsGot(response.products, response.total);
                        } else {
                            MessageService.showMessage(context.getString(R.string.unexpected_response_from_the_server), MessageService.Type.error, context);
                        }
                    } else {
                        MessageService.showMessage(response.answer.message, MessageService.Type.error, context);
                    }
                }
                LoadingService.end();
            }
        });
    }
}
