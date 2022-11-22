package ua.com.fennec.modules.afterAuthFlow.menuModule;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ua.com.fennec.R;

public class MenuRecycleAdapter extends RecyclerView.Adapter<MenuRecycleAdapter.ViewHolder> {

        private ArrayList<MenuButton> buttons;
        private LayoutInflater mInflater;

        private MenuRecycleAdapterOutput output;
        public MenuRecycleAdapter(Context context, ArrayList<MenuButton> buttons,MenuRecycleAdapterOutput output) {
            this.mInflater = LayoutInflater.from(context);
            this.buttons = buttons;
            this.output = output;
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
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    output.menuButtonTapped(buttons.get(position));
                }
            });
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