package ua.com.fennec.afterAuthFlow.menuModule;

import android.content.Context;
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

public class MenuRecycleAdapter extends RecyclerView.Adapter<MenuRecycleAdapter.ViewHolder> {

        private ArrayList<MenuButton> buttons;
        private LayoutInflater mInflater;

        public MenuRecycleAdapter(Context context, ArrayList<MenuButton> buttons) {
            this.mInflater = LayoutInflater.from(context);
            this.buttons = buttons;
        }


        @Override
        @NonNull
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = mInflater.inflate(R.layout.menu_recycle_view, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            MenuButtonData data = buttons.get(position).getTitle(mInflater.getContext());
            holder.imageView.setImageDrawable(data.image);
            holder.textView.setText(data.title);
        }
        @Override
        public int getItemCount() {
            return buttons.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            ImageView imageView;
            TextView textView;

            ViewHolder(View itemView) {
                super(itemView);
                imageView = itemView.findViewById(R.id.menuImageView);
                textView = itemView.findViewById(R.id.menuTextView);
            }
        }
 }
