package com.seven.fzuborrow.ui.mine;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.seven.fzuborrow.R;
import com.seven.fzuborrow.data.Apply;
import com.seven.fzuborrow.data.User;
import com.seven.fzuborrow.network.Api;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.seven.fzuborrow.Constants.APPLY_STATUS_FINISHED;
import static com.seven.fzuborrow.Constants.APPLY_STATUS_USING;
import static com.seven.fzuborrow.Constants.APPLY_STATUS_WAITING;

public class MyBorrowActivity extends AppCompatActivity {
    private MyApplyAdapter adapter;
    private List<Apply> list = new ArrayList<>();
    private SwipeRefreshLayout swipeRefresh;
    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cg_activity_my_borrow);
        setSupportActionBar(findViewById(R.id.manager_toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        swipeRefresh = findViewById(R.id.swipe_refresh);
        RecyclerView recyclerView = findViewById(R.id.manage_recyclerview);
        adapter = new MyApplyAdapter(list, this);
        recyclerView.setAdapter(adapter);
        refreshData();

        swipeRefresh.setOnRefreshListener(this::refreshData);

    }

    private void refreshData() {
        Api.get().findApply(User.getLoggedInUser().getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(findApplyResponse -> {
                    for (Apply apply : findApplyResponse.getApplyList()) {
                        if(apply.getStatus()==APPLY_STATUS_FINISHED
                        ||apply.getStatus()== APPLY_STATUS_WAITING
                        ||apply.getStatus()==APPLY_STATUS_USING) {
                            list.add(0,apply);
                        }
                    }
                    if (swipeRefresh.isRefreshing()) {
                        swipeRefresh.setRefreshing(false);
                    }
                    adapter.notifyDataSetChanged();
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
