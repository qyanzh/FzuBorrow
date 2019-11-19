package com.seven.fzuborrow.ui.login;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.seven.fzuborrow.MainActivity;
import com.seven.fzuborrow.R;
import com.seven.fzuborrow.data.User;
import com.seven.fzuborrow.network.Api;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class LoginActivity extends AppCompatActivity {

    TextInputEditText mAccountNumber;
    TextInputEditText mPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);
        setContentView(R.layout.activity_login);
        mAccountNumber = findViewById(R.id.et_username);
        mPassword = findViewById(R.id.et_student_password);
        Button btLogin = findViewById(R.id.bt_login);
        btLogin.setOnClickListener(v -> onLoginClicked());
        Button btRegister = findViewById(R.id.bt_register);
        btRegister.setOnClickListener(v -> onRegisterClicked());
    }

    @SuppressLint("CheckResult")
    private void onLoginClicked() {
        String username = mAccountNumber.getText().toString();
        String password = mPassword.getText().toString();
        //TODO:自动登录
        if(username.isEmpty()||password.isEmpty()) {
            username = "zqy";
            password = "12345";
        }
        String finalUsername = username;
        Api.get().login(username, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(loginResponse -> {
                    Toast.makeText(this, loginResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    if (loginResponse.getCode() == 200) {
                        User user = new User();
                        user.setToken(loginResponse.getToken());
                        User.setLoggedInUser(user);
                        Intent intent = new Intent(this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
    }

    @SuppressLint("CheckResult")
    private void onRegisterClicked() {
        String username = mAccountNumber.getText().toString();
        String password = mPassword.getText().toString();
        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "请输入用户名和密码", Toast.LENGTH_SHORT).show();
            return;
        }
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this)
                .setView(R.layout.dialog_register)
                .setTitle("绑定学号")
                .setPositiveButton("绑定", null)
                .setNegativeButton("取消", null);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        TextInputEditText mStudentNumber = alertDialog.findViewById(R.id.et_student_number);
        TextInputEditText mStudentPassword = alertDialog.findViewById(R.id.et_student_password);
        alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(v -> {
            String studentNumber = mStudentNumber.getText().toString();
            String studentPassword = mStudentPassword.getText().toString();
            if (studentNumber.isEmpty() || studentPassword.isEmpty()) {
                Toast.makeText(this, "请输入教务处学号和密码", Toast.LENGTH_SHORT).show();
                return;
            }
            Toast.makeText(this, "绑定中", Toast.LENGTH_SHORT).show();
            Api.get().register(username, password, studentNumber, studentPassword, "")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(registerResponse -> Toast.makeText(this, registerResponse.getMessage(), Toast.LENGTH_SHORT).show());
        });
        alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE).setOnClickListener(v -> {
            alertDialog.dismiss();
        });
    }
}
