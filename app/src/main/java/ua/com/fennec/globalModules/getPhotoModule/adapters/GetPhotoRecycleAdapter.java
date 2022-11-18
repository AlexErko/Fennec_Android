package ua.com.fennec.globalModules.getPhotoModule.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import ua.com.fennec.Constants;
import ua.com.fennec.R;
import ua.com.fennec.globalModules.getPhotoModule.interfaces.GetPhotoRecycleAdapterOutput;

public class GetPhotoRecycleAdapter extends RecyclerView.Adapter<GetPhotoRecycleAdapter.ViewHolder> {

        private ArrayList<String> photos;
        private LayoutInflater mInflater;
        private Boolean isPublic;
        private GetPhotoRecycleAdapterOutput output;
        public GetPhotoRecycleAdapter(Context context, ArrayList<String> photos, GetPhotoRecycleAdapterOutput output, Boolean isPublic) {
            this.mInflater = LayoutInflater.from(context);
            this.photos = photos;
            this.isPublic = isPublic;
            this.output = output;
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
            Glide.with(mInflater.getContext()).load(Constants.HOST + "/" + photos.get(position)).into(holder.imageView);
            if (isPublic) {
                holder.deleteButton.setVisibility(View.GONE);
            } else {
                holder.deleteButton.setVisibility(View.VISIBLE);
            }

            holder.deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    output.deleteTapped(photos.get(position));
                }
            });
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    output.imageTapped(photos.get(position));
                }
            });
        }

        // total number of cells
        @Override
        public int getItemCount() {
            return photos.size();
        }


        // stores and recycles views as they are scrolled off screen
        public class ViewHolder extends RecyclerView.ViewHolder {
            ImageView imageView;
            ImageView deleteButton;

            ViewHolder(View itemView) {
                super(itemView);
                imageView = itemView.findViewById(R.id.photoImageView);
                deleteButton = itemView.findViewById(R.id.deleteButton);
            }
        }
 }
