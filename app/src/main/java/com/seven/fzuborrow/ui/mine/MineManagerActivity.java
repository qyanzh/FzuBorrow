package com.seven.fzuborrow.ui.mine;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.seven.fzuborrow.Constants;
import com.seven.fzuborrow.R;
import com.seven.fzuborrow.data.Good;
import com.seven.fzuborrow.data.User;
import com.seven.fzuborrow.network.Api;
import com.seven.fzuborrow.ui.home.GoodsAdapter;
import com.seven.fzuborrow.ui.home.detail.GoodDetailActivity;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MineManagerActivity extends AppCompatActivity {
    private GoodsAdapter adapter;
    private List<Good> list;
    private List<Good> goodList = new ArrayList<>();
    private List<Good> roomList = new ArrayList<>();


    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cg_activity_mine_manager);
        setSupportActionBar(findViewById(R.id.manager_toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        RecyclerView recyclerView = findViewById(R.id.manage_recyclerview);
        adapter = new GoodsAdapter(good -> {
            Intent intent = new Intent(this, GoodDetailActivity.class)
                    .putExtra("good", good);
            startActivity(intent);
        });
        adapter.addOnTabClickListener(type -> {
            if (type.equals("活动室")) {
                adapter.submitList(roomList);
            } else if (type.equals("个人闲置")) {
                adapter.submitList(goodList);
            }
        });
        adapter.setBannerVisible(false);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        refreshGoods();
    }

    @SuppressLint("CheckResult")
    private void refreshGoods() {
        Api.get().findAllGoodByUid(User.getLoggedInUser().getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(findAllGoodsResponse -> {
                    list = findAllGoodsResponse.getGoodList();
                    for (Good good : list) {
                        if (good.getType().equals(Constants.GOOD_TYPE_GOOD)) {
                            goodList.add(good);
                        } else {
                            roomList.add(good);
                        }
                    }
                    adapter.submitList(roomList);
                }, e -> Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show());
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
