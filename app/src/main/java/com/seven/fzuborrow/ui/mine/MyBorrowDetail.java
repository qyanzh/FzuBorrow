package com.seven.fzuborrow.ui.mine;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.seven.fzuborrow.R;
import com.seven.fzuborrow.data.Good;

public class MyBorrowDetail extends AppCompatActivity {
    Good data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cg_activity_my_borrow_detail);
        setSupportActionBar(findViewById(R.id.borrow_detail_toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("借呗");
        //为了得到对象
         data =(Good) getIntent().getSerializableExtra("data");
         initData();//负责将数据填入该有的栏里面


    }
    private void initData(){ // 负责填数据
        TextView detail= (TextView) findViewById(R.id.my_borrow_detail);
        detail.setText(data.getDetail());
        TextView name = (TextView) findViewById(R.id.detail_name);
        name.setText(data.getName());
        ImageView picture = (ImageView) findViewById(R.id.detail_picture);
        Glide.with(getApplicationContext())
                .load(data.getImgurl())
                .into(picture);

    }
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }


}
