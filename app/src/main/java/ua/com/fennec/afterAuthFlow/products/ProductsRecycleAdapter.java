package ua.com.fennec.afterAuthFlow.products;

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
import ua.com.fennec.globalModules.getPhotoModule.interfaces.GetPhotoRecycleAdapterOutput;
import ua.com.fennec.models.Product;

public class ProductsRecycleAdapter extends RecyclerView.Adapter<ProductsRecycleAdapter.ViewHolder> {

    private ArrayList<Product> products;
    private LayoutInflater mInflater;
    public static final int FOOTER_VIEW = 1;

    public ProductsRecycleAdapter(Context context, ArrayList<Product> products) {
        this.mInflater = LayoutInflater.from(context);
        this.products = products;
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

            Log.d(Constants.TAG, "FooterViewHolder");
        } else {
            Glide.with(mInflater.getContext()).load(Constants.HOST + "/" + products.get(position).image).into(holder.imageView);
            holder.nameTextView.setText(products.get(position).name);
            holder.priceTextView.setText(products.get(position).price);
        }
    }

    // total number of cells
    @Override
    public int getItemCount() {
        return products.size() + 1;
    }


    @Override
    public int getItemViewType(int position) {
        Log.d(Constants.TAG, "getItemViewType " + position + "     count " + products.size());

        if (position == products.size() && products.size() != 0) {
            Log.d(Constants.TAG, "getItemViewType " + position);

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
                    // Do whatever you want on clicking the item
                }
            });
        }
    }

}
