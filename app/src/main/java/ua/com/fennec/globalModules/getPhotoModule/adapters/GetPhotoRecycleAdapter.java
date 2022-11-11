package ua.com.fennec.globalModules.getPhotoModule.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import ua.com.fennec.Constants;
import ua.com.fennec.R;

public class GetPhotoRecycleAdapter extends RecyclerView.Adapter<GetPhotoRecycleAdapter.ViewHolder> {

        private ArrayList<String> photos;
        private LayoutInflater mInflater;
        public GetPhotoRecycleAdapter(Context context, ArrayList<String> photos) {
            this.mInflater = LayoutInflater.from(context);
            this.photos = photos;
        }


        @Override
        @NonNull
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = mInflater.inflate(R.layout.get_photo_recycle_view, parent, false);
            return new ViewHolder(view);
        }

        // binds the data to the TextView in each cell
        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Picasso.with(mInflater.getContext()).load(Constants.HOST + "/" + photos.get(position)).into(holder.imageView);
        }

        // total number of cells
        @Override
        public int getItemCount() {
            return photos.size();
        }


        // stores and recycles views as they are scrolled off screen
        public class ViewHolder extends RecyclerView.ViewHolder {
            ImageView imageView;

            ViewHolder(View itemView) {
                super(itemView);
                imageView = itemView.findViewById(R.id.photoImageView);
            }
        }
 }
