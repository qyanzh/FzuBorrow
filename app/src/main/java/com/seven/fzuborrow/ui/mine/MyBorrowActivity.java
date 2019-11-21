package com.seven.fzuborrow.ui.mine;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.seven.fzuborrow.R;
import com.seven.fzuborrow.data.Apply;
import com.seven.fzuborrow.data.User;
import com.seven.fzuborrow.network.Api;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MyBorrowActivity extends AppCompatActivity {
    private MyApplyAdapter adapter;
    private List<Apply> list;

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cg_activity_my_borrow);
        setSupportActionBar(findViewById(R.id.manager_toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        RecyclerView recyclerView = findViewById(R.id.manage_recyclerview);
        Api.get().findApply(User.getLoggedInUser().getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(findApplyResponse -> {
                    adapter = new MyApplyAdapter(findApplyResponse.getApplyList(), this);
                    recyclerView.setAdapter(adapter);
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
