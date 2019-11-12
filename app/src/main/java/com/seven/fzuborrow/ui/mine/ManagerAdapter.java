package com.seven.fzuborrow.ui.mine;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.seven.fzuborrow.R;
import com.seven.fzuborrow.data.Good;
import com.seven.fzuborrow.ui.home.detail.GoodDetailActivity;


import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class ManagerAdapter extends RecyclerView.Adapter<ManagerAdapter.ViewHolder> {

    private List<Good> list;
    private Context context;
    Good data;
    static class ViewHolder extends RecyclerView.ViewHolder{
        View goodView;
        LinearLayout linearLayout;
        LinearLayout linearLayout1;
        ImageView icon;
        ImageView status_icon;
        TextView name;
        TextView status;
        LinearLayout pingjia;
        LinearLayout phone;
        public ViewHolder(View view){
            super(view);
            goodView = view;
            status_icon =view.findViewById(R.id.manager_item_status_icon);
            linearLayout = (LinearLayout) view.findViewById(R.id.recycler_view_linearLayout);
            linearLayout1 = (LinearLayout) view.findViewById(R.id.manager_item_dianji);
            icon = (ImageView) view.findViewById(R.id.manager_item_icon);
            name = (TextView) view.findViewById(R.id.manager_item_name);
            status = (TextView) view.findViewById(R.id.manager_item_status);
            pingjia = view.findViewById(R.id.manager_item_pingjia);
            phone = view.findViewById(R.id.manager_item_phone);
        }
    }
    public ManagerAdapter(List<Good> list,Context context){
        this.list=list;
        this.context=context;
    }


    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cg_manager_item,parent,false);

        final ViewHolder holder = new ViewHolder(view);
        holder.goodView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                data = list.get(position);
                Intent intent = new Intent(context,MyBorrowDetail.class);
                intent.putExtra("data",  data);
                context.startActivity(intent);
                //打算的是，点击之后，把信息传给下个activity。就不要再让下个activity再去申请了。
            }
        });
        holder.phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPhoneDialog();
            }
        });
        holder.pingjia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEvaluationDialog();
            }
        });
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

    private void showEvaluationDialog(){
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(context)
                .setView(R.layout.cg_dialog_evaluation)
                .setPositiveButton("提交", null)
                .setNegativeButton("取消", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    @Override
    public void onBindViewHolder(@NonNull ManagerAdapter.ViewHolder holder, int position) {
         data = list.get(position);
        /*这边设置图片和金额以及状态。*/
        holder.name.setText(data.getName());

        Glide.with(context)
                .load(data.getImgurl())
                .into(holder.icon);
        Log.d("url我",data.getImgurl());
 /*    */    // status: 0 未借出    1  租借中    2 已归还  3 超时
        //得设置图片和名字 以及颜色
        if(data.getStatus()==0){
            holder.pingjia.setVisibility(View.GONE);
            holder.phone.setVisibility(View.GONE);
            holder.status.setText("未借出");
            holder.status.setTextColor(Color.parseColor("#0594D7"));
            holder.status_icon.setImageResource(R.drawable.cgh_item_weijiechu);
        }else if(data.getStatus() == 1){
            holder.pingjia.setVisibility(View.GONE);
            holder.status.setText("租借中");
            holder.status.setTextColor(Color.parseColor("#EB402F"));
            holder.status_icon.setImageResource(R.drawable.cgh_item_jieyongzhong);
        }else if(data.getStatus() == 2 ){
            holder.status_icon.setImageResource(R.drawable.cgh_item_yiguihuan);
            holder.status.setText("已归还");
            holder.status.setTextColor(Color.parseColor("#101010"));
        }


    }

    @Override
    public int getItemCount() {
        return list.size();
    }






}
