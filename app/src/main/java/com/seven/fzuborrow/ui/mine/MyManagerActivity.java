package com.seven.fzuborrow.ui.mine;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.seven.fzuborrow.R;
import android.os.Bundle;

public class MyManagerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cg_activity_my_manager);
        RecyclerView recyclerView =(RecyclerView) findViewById(R.id.manage_recyclerview);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));

    }


}
