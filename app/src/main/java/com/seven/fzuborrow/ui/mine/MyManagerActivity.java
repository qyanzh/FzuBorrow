package com.seven.fzuborrow.ui.mine;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.seven.fzuborrow.R;
import com.seven.fzuborrow.data.Good;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
        setSupportActionBar(findViewById(R.id.manager_toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        RecyclerView recyclerView =(RecyclerView) findViewById(R.id.manage_recyclerview);
        list = new ArrayList<>();

     /*这里往下*/ // 在这里需要设置的属性是
        // 基本的gid，name，detail，url，status，money（待定好像）





        Good g1 = new Good(9,"螺丝刀","这是一把惊天地泣鬼神的螺丝刀，千万不能拿来拧螺丝");
        g1.setImgurl("https://i.loli.net/2019/11/06/gfTaEAuw95yqN3O.jpg");
        g1.setStatus(1);
        list.add(g1);

        Good g2 = new Good(12,"铅笔","这是一根没有笔芯的铅笔");
        g2.setImgurl( "https://i.loli.net/2019/11/06/1GuQCMKY6aXpT7m.jpg");
        g2.setStatus(2);
        list.add(g2);

        Good g3 = new Good(10,"高数书","这是一本神奇的书，一本属于挂科人的书");
        g3.setImgurl("https://i.loli.net/2019/11/06/x2wVG1dhzFUABKv.jpg");
        g3.setStatus(0);
        list.add(g3);

        Good g4 = new Good(11,"耳机","在这里，你可以体验单声道的快乐");
        g4.setImgurl("https://i.loli.net/2019/11/06/igqEHyIhJpvuBO4.jpg");
        g4.setStatus(0);
        list.add(g4);

        //long id, String name, String profile
     /*到这里都是构造list的过程，属于后期修改部分*/
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        adapter = new ManagerAdapter(list,this);
        recyclerView.setAdapter(adapter);


//        findViewById(R.id.manager_back).setOnClickListener(this::onClick);
//        findViewById(R.id.manager_back_text).setOnClickListener(this::onClick);


       // Log.d("走完主进程","你好");
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
//            case R.id.manager_back:
//                onBackPressed();
//                break;
//            case R.id.manager_back_text:
//                onBackPressed();
//                break;

            case android.R.id.home:
                onBackPressed();
                break;
        }

        return true;
    }
}
