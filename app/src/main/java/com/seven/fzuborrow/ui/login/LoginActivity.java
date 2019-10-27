package com.seven.fzuborrow.ui.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.seven.fzuborrow.MainActivity;
import com.seven.fzuborrow.R;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText mAccountNumber;
    private TextInputEditText mPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.LoginTheme);
        setContentView(R.layout.activity_login);
        mAccountNumber = findViewById(R.id.et_account_number);
        mPassword = findViewById(R.id.et_password);
        FloatingActionButton fab = findViewById(R.id.fab_sign_in);
        fab.setOnClickListener(v-> onLoginClicked());
    }

    private void onLoginClicked() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
