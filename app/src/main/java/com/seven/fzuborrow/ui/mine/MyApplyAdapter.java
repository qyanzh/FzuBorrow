package com.seven.fzuborrow.ui.mine;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.LongSparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.seven.fzuborrow.R;
import com.seven.fzuborrow.data.Apply;
import com.seven.fzuborrow.data.Good;
import com.seven.fzuborrow.data.User;
import com.seven.fzuborrow.network.Api;
import com.seven.fzuborrow.ui.home.detail.GoodDetailActivity;
import com.seven.fzuborrow.ui.mine.restore.RestoreActivity;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.seven.fzuborrow.Constants.APPLY_STATUS_FINISHED;
import static com.seven.fzuborrow.Constants.APPLY_STATUS_PENDING;
import static com.seven.fzuborrow.Constants.APPLY_STATUS_REJECTED;
import static com.seven.fzuborrow.Constants.APPLY_STATUS_USING;
import static com.seven.fzuborrow.Constants.APPLY_STATUS_WATING;

public class MyApplyAdapter extends RecyclerView.Adapter<MyApplyAdapter.ViewHolder> {

    private List<Apply> list;
    private Context context;
    private LongSparseArray goodMap = new LongSparseArray<Good>();

    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView img;
        TextView name;
        TextView price;
        ImageView status_icon;
        TextView status;
        View returnGood;
        View contact;

        public ViewHolder(View view) {
            super(view);
            img = view.findViewById(R.id.manager_item_img);
            name = view.findViewById(R.id.manager_item_name);
            price = view.findViewById(R.id.manager_item_prize);
            status_icon = view.findViewById(R.id.manager_item_status_icon);
            status = view.findViewById(R.id.manager_item_status);
            contact = view.findViewById(R.id.manager_item_contact);
            returnGood = view.findViewById(R.id.manager_item_return);
        }
    }

    public MyApplyAdapter(List<Apply> list, Context context) {
        this.list = list;
        this.context = context;
    }


    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cg_manager_item, parent, false);

        final ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    private void showPhoneDialog() {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(context)
                .setView(R.layout.cg_dialog_phone)
                .setPositiveButton("拨打", null)
                .setNegativeButton("取消", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    @SuppressLint("CheckResult")
    @Override
    public void onBindViewHolder(@NonNull MyApplyAdapter.ViewHolder holder, int position) {
        Apply apply = list.get(position);
        Good good = (Good) goodMap.get(apply.getGid());
        if (good == null) {
            Api.get().findGood(User.getLoggedInUser().getToken(), apply.getGid())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(findGoodResponse -> {
                                goodMap.put(apply.getGid(), findGoodResponse.getGood());
                                bindView(holder, apply, (Good) goodMap.get(apply.getGid()));
                            }
                            , e -> Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show());
        } else {
            bindView(holder, apply, good);
        }

    }

    private void bindView(@NonNull ViewHolder holder, Apply apply, Good good) {
        holder.name.setText(good.getName());
        Glide.with(context)
                .load(good.getImgurl())
                .into(holder.img);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, GoodDetailActivity.class);
            intent.putExtra("good", good);
            context.startActivity(intent);
        });
        holder.contact.setOnClickListener(v -> showPhoneDialog());
        holder.returnGood.setOnClickListener(v -> {
            Intent intent = new Intent(context, RestoreActivity.class).putExtra("apply", apply);
            context.startActivity(intent);
        });


        if (apply.getStatus() == APPLY_STATUS_PENDING) {
            holder.returnGood.setVisibility(View.GONE);
            holder.contact.setVisibility(View.GONE);
            holder.status.setText("审核中");
            holder.status.setTextColor(Color.parseColor("#0594D7"));
            holder.status_icon.setImageResource(R.drawable.cgh_item_weijiechu);
        } else if (apply.getStatus() == APPLY_STATUS_USING) {
            holder.returnGood.setVisibility(View.VISIBLE);
            holder.status.setText("租借中");
            holder.status.setTextColor(Color.parseColor("#EB402F"));
            holder.status_icon.setImageResource(R.drawable.cgh_item_jieyongzhong);
        } else if (apply.getStatus() == APPLY_STATUS_FINISHED) {
            holder.status_icon.setImageResource(R.drawable.cgh_item_yiguihuan);
            holder.status.setText("已归还");
            holder.status.setTextColor(Color.parseColor("#101010"));
        } else if (apply.getStatus() == APPLY_STATUS_REJECTED) {
            holder.returnGood.setVisibility(View.GONE);
            holder.contact.setVisibility(View.GONE);
            holder.status.setText("已拒绝");
            holder.status.setTextColor(Color.parseColor("#101010"));
            holder.status_icon.setImageResource(R.drawable.cgh_item_weijiechu);
        } else if (apply.getStatus() == APPLY_STATUS_WATING) {
            holder.returnGood.setVisibility(View.GONE);
            holder.contact.setVisibility(View.GONE);
            holder.status.setText("归还审核中");
            holder.status.setTextColor(Color.parseColor("#0594D7"));
            holder.status_icon.setImageResource(R.drawable.cgh_item_weijiechu);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}
