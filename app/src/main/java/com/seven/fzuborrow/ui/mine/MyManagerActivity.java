package com.seven.fzuborrow.ui.mine;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.seven.fzuborrow.R;
import com.seven.fzuborrow.data.Good;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MyManagerActivity extends AppCompatActivity {
    private ManagerAdapter adapter;
    private List<Good> list;
    ImageView back;
    TextView back_text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cg_activity_my_manager);

        RecyclerView recyclerView =(RecyclerView) findViewById(R.id.manage_recyclerview);
        list = new ArrayList<>();
        list.add(new Good(15,"名字","详情"));
        list.add(new Good(15,"名字","详情"));
        list.add(new Good(15,"名字","详情"));//long id, String name, String profile
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        adapter = new ManagerAdapter(list,this);
        recyclerView.setAdapter(adapter);


//        findViewById(R.id.manager_back).setOnClickListener(this::onClick);
//        findViewById(R.id.manager_back_text).setOnClickListener(this::onClick);


       // Log.d("走完主进程","你好");
    }


}
