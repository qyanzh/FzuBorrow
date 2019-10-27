package com.seven.fzuborrow.ui.home;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.seven.fzuborrow.R;
import com.seven.fzuborrow.data.Good;

public class GoodsAdapter extends ListAdapter<Good, RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private static final int TYPE_FOOTER = 2;

    protected GoodsAdapter() {
        super(new DiffUtil.ItemCallback<Good>() {
            @Override
            public boolean areItemsTheSame(@NonNull Good oldItem, @NonNull Good newItem) {
                return oldItem.getId() == newItem.getId();
            }

            @Override
            public boolean areContentsTheSame(@NonNull Good oldItem, @NonNull Good newItem) {
                return oldItem.getName().equals(newItem.getName());
            }
        });
    }

    private static final String TAG = "GoodsAdater";

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.good_item, parent, false);
            final ItemViewHolder holder = new ItemViewHolder(view);
            holder.itemView.setOnClickListener(v -> Log.d(TAG, "onCreateViewHolder: " + holder.name));
            return holder;
        } else if (viewType == TYPE_HEADER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.banner_item, parent, false);
            final HeaderViewHolder holder = new HeaderViewHolder(view);
            holder.itemView.setOnClickListener(v -> {});
            StaggeredGridLayoutManager.LayoutParams layoutParams = (StaggeredGridLayoutManager.LayoutParams) holder.itemView.getLayoutParams();
            layoutParams.setFullSpan(true);
            holder.itemView.setLayoutParams(layoutParams);
            return holder;
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.load_item, parent, false);
            final FooterViewHolder holder = new FooterViewHolder(view);
            holder.itemView.setOnClickListener(v -> {});
            StaggeredGridLayoutManager.LayoutParams layoutParams = (StaggeredGridLayoutManager.LayoutParams) holder.itemView.getLayoutParams();
            layoutParams.setFullSpan(true);
            holder.itemView.setLayoutParams(layoutParams);
            return holder;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            Good good = getItem(position-1);
            ((ItemViewHolder) holder).name.setText(good.getName());
            ((ItemViewHolder) holder).profile.setText(good.getProfile());
        }
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView profile;

        ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.good_name);
            profile = itemView.findViewById(R.id.good_profile);
        }
    }

    static class HeaderViewHolder extends RecyclerView.ViewHolder {

        HeaderViewHolder(@NonNull View itemView) {
            super(itemView);

        }
    }

    static class FooterViewHolder extends RecyclerView.ViewHolder {
        FooterViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    @Override
    public int getItemCount() {
        return super.getItemCount()+2;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEADER;
        } else if (position == getItemCount()-1) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }
}
