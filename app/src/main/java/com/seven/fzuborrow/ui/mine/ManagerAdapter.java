package com.seven.fzuborrow.ui.mine;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.seven.fzuborrow.R;
import com.seven.fzuborrow.data.Good;
import com.seven.fzuborrow.ui.community.TieziAdapter;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class ManagerAdapter extends RecyclerView.Adapter<ManagerAdapter.ViewHolder> {

    private List<Good> list;
    private Context context;
    static class ViewHolder extends RecyclerView.ViewHolder{
        View goodView;
        LinearLayout linearLayout;
        public ViewHolder(View view){
            super(view);
            goodView = view;
            linearLayout = (LinearLayout) view.findViewById(R.id.recycler_view_linearLayout);
        }
    }
    public ManagerAdapter(List<Good> list,Context context){
        this.list=list;
        this.context=context;
    }


    @Override
    public ManagerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cg_manager_item,parent,false);

        final ViewHolder holder = new ViewHolder(view);
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int postion = holder.getAdapterPosition();
                Intent intent = new Intent(context,MyBorrowDetail.class);
                context.startActivity(intent);
            }
        });

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ManagerAdapter.ViewHolder holder, int position) {
        Good data = list.get(position);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }






}
