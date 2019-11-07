package com.seven.fzuborrow.ui.community;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.seven.fzuborrow.R;
import com.seven.fzuborrow.data.Disc;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class DiscAdapter extends ListAdapter<Disc, DiscAdapter.ViewHolder> {

    private SimpleDateFormat f = new SimpleDateFormat("MM月dd日 HH:mm", Locale.getDefault());

    private DiscClickListener listener;

    DiscAdapter(DiscClickListener listener) {
        super(new DiffUtil.ItemCallback<Disc>() {
            @Override
            public boolean areItemsTheSame(@NonNull Disc oldItem, @NonNull Disc newItem) {
                return oldItem.getDid() == newItem.getDid();
            }

            @Override
            public boolean areContentsTheSame(@NonNull Disc oldItem, @NonNull Disc newItem) {
                return oldItem.getDid() == newItem.getDid();
            }
        });
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.disc_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Disc disc = getItem(position);
        holder.tvUsername.setText(disc.getUsername());
        Glide.with(holder.itemView).load(disc.getUseravatar()).into(holder.ivUserAvatar);
        holder.tvPublishTime.setText(f.format(disc.getCtime()*1000L));
        holder.tvDetail.setText(disc.getTitle());
        Glide.with(holder.itemView).load(disc.getImgurl()).into(holder.ivImage);
        holder.tvComments.setText(disc.getCounts()+"");
        holder.tvLikes.setText(disc.getLikes()+"");
        holder.itemView.setOnClickListener(v->{
            listener.onClick(disc);
        });
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivUserAvatar;
        TextView tvUsername;
        TextView tvPublishTime;
        TextView tvDetail;
        ImageView ivImage;
        TextView tvComments;
        TextView tvLikes;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivUserAvatar = itemView.findViewById(R.id.iv_user_image);
            tvUsername = itemView.findViewById(R.id.tv_user_name);
            tvPublishTime = itemView.findViewById(R.id.tv_time);
            tvDetail = itemView.findViewById(R.id.tv_content);
            ivImage = itemView.findViewById(R.id.iv_image);
            tvComments = itemView.findViewById(R.id.tv_comment_count);
            tvLikes = itemView.findViewById(R.id.tv_like_count);

        }
    }

    interface DiscClickListener {
        void onClick(Disc disc);
    }

}
