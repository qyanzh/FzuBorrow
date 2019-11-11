package com.seven.fzuborrow.ui.home.detail;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.view.TimePickerView;
import com.bumptech.glide.Glide;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.seven.fzuborrow.Constants;
import com.seven.fzuborrow.R;
import com.seven.fzuborrow.data.Good;
import com.seven.fzuborrow.data.User;
import com.seven.fzuborrow.network.Api;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class GoodDetailActivity extends AppCompatActivity {

    Good good;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_good_detail);
        setSupportActionBar(findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        good = getIntent().getParcelableExtra("good");
        getSupportActionBar().setTitle(good.getName());

        ImageView ivGood = findViewById(R.id.iv_image);
        if (good.hasImage()) {
            Glide.with(this).load(good.getImgurl()).into(ivGood);
        }

        Button btBorrow = findViewById(R.id.bt_borrow);
        btBorrow.setOnClickListener(v -> showBorrowDialog());
        //TODO:判断当前用户是否是拥有者
        if (good.getStatus() == Constants.GOOD_STATUS_BORROWED) {
            btBorrow.setBackground(getDrawable(R.drawable.corner_button_grey));
            btBorrow.setText("已借出");
        }

        TextView tvDetail = findViewById(R.id.tv_good_detail);
        tvDetail.setText(good.getDetail());

        TextView tvOwnerName = findViewById(R.id.tv_owner_name);
        TextView tvOwnerContact = findViewById(R.id.tv_owner_contact);

        tvOwnerName.setText(good.getUid() + "");

        //TODO:查询拥有者姓名和联系方式


    }

    private Calendar startTime;
    private Calendar endTime;

    @SuppressLint("CheckResult")
    private void showBorrowDialog() {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this)
                .setView(R.layout.dialog_borrow)
                .setPositiveButton("拨打", null)
                .setNegativeButton("取消", null);
        AlertDialog dialog = builder.create();
        dialog.show();

        TextView tvStartTime = dialog.findViewById(R.id.tv_start_time);
        TextView tvEndTime = dialog.findViewById(R.id.tv_end_time);
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());

        startTime = Calendar.getInstance();
        endTime = Calendar.getInstance();
        endTime.setTimeInMillis(startTime.getTime().getTime() + 24 * 60 * 60 * 1000L);

        tvStartTime.setText("预约时间: " + f.format(startTime.getTime()) + " >");
        tvEndTime.setText("归还时间: " + f.format(endTime.getTime()) + " >");


        tvStartTime.setOnClickListener(v -> {
            TimePickerView timePickerView = new TimePickerBuilder(this, (date, v1) -> {
                tvStartTime.setText("预约时间: " + f.format(date) + " >");
                startTime.setTime(date);
            }).setType(new boolean[]{true, true, true, true, true, false}).setDate(startTime).isDialog(true).build();
            customPickerView(timePickerView);
            timePickerView.show();
        });

        tvEndTime.setOnClickListener(v -> {
            TimePickerView timePickerView = new TimePickerBuilder(this, (date, v1) -> {
                tvEndTime.setText("归还时间: " + f.format(date) + " >");
                endTime.setTime(date);
            }).setType(new boolean[]{true, true, true, true, true, false}).setDate(endTime).isDialog(true).build();
            customPickerView(timePickerView);
            timePickerView.show();
        });

        EditText etReason = dialog.findViewById(R.id.et_reason);
        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(v -> {
            if (startTime.getTimeInMillis() < endTime.getTimeInMillis()) {
                String reason = etReason.getText().toString();
                if(!reason.isEmpty()) {
                    Api.get().applyGood(User.getLoggedInUser().getToken(), good.getGid(), good.getUid(), reason,
                            startTime.getTimeInMillis() / 1000, endTime.getTimeInMillis() / 1000)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(applyResponse -> {
                                Toast.makeText(this, applyResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                if (applyResponse.getCode() == 200) dialog.dismiss();
                            });
                } else {
                    Toast.makeText(this, "请填写申请信息", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "归还时间需大于预约时间", Toast.LENGTH_SHORT).show();
            }
        });
        dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setOnClickListener(v -> dialog.dismiss());

    }

    private void customPickerView(TimePickerView timePickerView) {
        timePickerView.getDialog().getWindow().setGravity(Gravity.BOTTOM);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM);
        params.leftMargin = 0;
        params.rightMargin = 0;
        timePickerView.getDialog().findViewById(R.id.content_container).setLayoutParams(params);
        Button submitButton = (Button) timePickerView.findViewById(R.id.btnSubmit);
        submitButton.setBackground(null);
        submitButton.setTextColor(getResources().getColor(R.color.colorAccent));
        Button cancelButton = (Button) timePickerView.findViewById(R.id.btnCancel);
        cancelButton.setBackground(null);
        cancelButton.setTextColor(getResources().getColor(R.color.colorAccent));
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }
}
