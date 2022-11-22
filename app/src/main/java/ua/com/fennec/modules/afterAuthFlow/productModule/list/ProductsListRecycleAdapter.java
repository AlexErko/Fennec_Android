package ua.com.fennec.modules.afterAuthFlow.productModule.list;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import ua.com.fennec.Constants;
import ua.com.fennec.R;
import ua.com.fennec.modules.afterAuthFlow.productModule.list.interfaces.ProductsListRecycleAdapterOutput;
import ua.com.fennec.models.Product;

public class ProductsListRecycleAdapter extends RecyclerView.Adapter<ProductsListRecycleAdapter.ViewHolder> {

    private ArrayList<Product> products;
    private LayoutInflater mInflater;
    private Boolean showButtonMore;
    private ProductsListRecycleAdapterOutput output;
    public static final int FOOTER_VIEW = 1;

    public ProductsListRecycleAdapter(Context context, ArrayList<Product> products, ProductsListRecycleAdapterOutput output, Boolean showButtonMore) {
        this.mInflater = LayoutInflater.from(context);
        this.products = products;
        this.showButtonMore = showButtonMore;
        this.output = output;
    }


    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == FOOTER_VIEW) {
            return new FooterViewHolder(mInflater.inflate(R.layout.products_show_more_view, parent, false));
        }
        View view = mInflater.inflate(R.layout.standart_recycle_view, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each cell
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if (holder instanceof FooterViewHolder) {

        } else {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(Constants.TAG, "onCLieck");
                    output.productTapped(products.get(position));
                }
            });
            Glide.with(mInflater.getContext()).load(Constants.HOST + "/" + products.get(position).image).into(holder.imageView);
            holder.nameTextView.setText(products.get(position).name);
            holder.priceTextView.setText(products.get(position).price);
        }
    }

    // total number of cells
    @Override
    public int getItemCount() {
        return products.size() + (showButtonMore ? 1 : 0);
    }


    @Override
    public int getItemViewType(int position) {
        if (position == products.size()) {
            return FOOTER_VIEW;
        }
        return super.getItemViewType(position);
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView nameTextView;
        TextView priceTextView;


        ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            nameTextView = itemView.findViewById(R.id.title);
            priceTextView = itemView.findViewById(R.id.subtitle);

        }
    }
    public class FooterViewHolder extends ViewHolder {
        public FooterViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    output.showMoreTapped();
                }
            });
        }
    }

}
