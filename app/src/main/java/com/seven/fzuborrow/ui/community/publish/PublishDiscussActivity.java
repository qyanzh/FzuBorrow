package com.seven.fzuborrow.ui.community.publish;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.seven.fzuborrow.Constants;
import com.seven.fzuborrow.R;
import com.seven.fzuborrow.data.User;
import com.seven.fzuborrow.network.Api;
import com.seven.fzuborrow.utils.ImageUtilKt;

import java.io.File;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;


public class PublishDiscussActivity extends AppCompatActivity {

    private String imagePath;

    private ImageView ivAddImage;
    private static final String TAG = "PublishDiscussActivity";

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_discuss);
        ivAddImage = findViewById(R.id.iv_add_image);
        ivAddImage.setOnClickListener(v -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    pickImage();
                } else {
                    requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE}, 0);
                }
            } else {
                pickImage();
            }
        });
        EditText etContent = findViewById(R.id.et_content);
        findViewById(R.id.bt_back).setOnClickListener(v -> onBackPressed());
        findViewById(R.id.bt_publish).setOnClickListener(v -> {
            String content = etContent.getText().toString();
            if (imagePath != null) {
                File file = new File(imagePath);
                RequestBody fileBody = RequestBody.create(file, MediaType.parse("image/png"));
                MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", file.getName(), fileBody);
                Api.get().uploadFile(User.getLoggedInUser().getToken(), filePart, Constants.UPLOAD_TYPE_DISC)
                        .subscribeOn(Schedulers.io())
                        .flatMap(uploadFileResponse -> Api.get().createDisc(User.getLoggedInUser().getToken(), content, User.getLoggedInUser().getUsername()))
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(createDiscResponse -> {
                            Toast.makeText(this, createDiscResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            onBackPressed();
                        },e-> Toast.makeText(this, "网络连接异常", Toast.LENGTH_SHORT).show());
            }
        });
    }

    private void pickImage() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 0:
                if (data != null) {
                    imagePath = ImageUtilKt.convertImageUriToPath(this, data);
                    Glide.with(this).load(imagePath).into(ivAddImage);
                }
                break;
        }
    }


}
