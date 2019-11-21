package com.seven.fzuborrow.ui.home.add

import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.seven.fzuborrow.Constants
import com.seven.fzuborrow.R
import com.seven.fzuborrow.data.User
import com.seven.fzuborrow.network.Api
import com.seven.fzuborrow.network.response.AddGoodResponse
import com.seven.fzuborrow.utils.convertImageUriToPath
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_add.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class AddActivity : AppCompatActivity() {

    private var imagePath: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
        setSupportActionBar(toolbar)
        supportActionBar?.title = ""
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close_black_24dp)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        subscribeUi()
    }

    private fun subscribeUi() {
        iv_add_image.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    pickImage()
                } else {
                    requestPermissions(arrayOf(WRITE_EXTERNAL_STORAGE), 0)
                }
            } else {
                pickImage()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.add_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
            R.id.menu_send -> publishGood()
        }
        return true
    }

    @SuppressLint("CheckResult")
    private fun publishGood() {
        et_location
        tab_layout.selectedTabPosition
        if (imagePath != null) {
            val file = File(imagePath)
            val fileBody = file.asRequestBody("image/png".toMediaTypeOrNull())
            val filePart = MultipartBody.Part.createFormData("file", file.name, fileBody)
            Api.get().uploadFile(User.getLoggedInUser().token, filePart, Constants.UPLOAD_TYPE_GOOD)
                .subscribeOn(Schedulers.io())
                .flatMap<AddGoodResponse> { uploadFileResponse ->
                    Api.get().addGood(
                        User.getLoggedInUser().token,
                        et_title.text.toString(),
                        if (tab_layout.selectedTabPosition == 0) Constants.GOOD_TYPE_GOOD else Constants.GOOD_TYPE_ROOM,
                        et_content.text.toString(),
                        et_location.text.toString(),
                        0.0,//TODO:价格
                        uploadFileResponse.data.imgurl
                    )
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ addGoodResponse ->
                    Toast.makeText(this, addGoodResponse.message, Toast.LENGTH_SHORT).show()
                    onBackPressed()
                }, { e ->
                    Toast.makeText(this, "网络连接异常", Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                })
        } else {
            Toast.makeText(this, "请至少上传一张图片", Toast.LENGTH_SHORT).show()
        }
    }

    private fun pickImage() {
        val intent = Intent("android.intent.action.GET_CONTENT")
        intent.type = "image/*"
        startActivityForResult(intent, 0)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            0 -> if (data != null) {
                imagePath = convertImageUriToPath(this, data)
                Glide.with(this).load(imagePath).into(iv_add_image)
            }
        }
    }
}
