package com.seven.fzuborrow.ui.login

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
import com.nanchen.compresshelper.CompressHelper
import com.seven.fzuborrow.Constants
import com.seven.fzuborrow.MainActivity
import com.seven.fzuborrow.R
import com.seven.fzuborrow.data.User
import com.seven.fzuborrow.network.Api
import com.seven.fzuborrow.utils.convertImageUriToPath
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_register.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class UserInfoActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            if (!intent.getBooleanExtra("register", false)) {
                toolbar_title.text = "个人资料"
                setDisplayHomeAsUpEnabled(true)
                setHomeAsUpIndicator(R.drawable.ic_close_black_24dp)
            }
            title = ""
        }

        Glide.with(this).load(User.getLoggedInUser().imgurl).into(iv_avatar)

        User.getLoggedInUser().apply {
            username?.let { et_username.setText(it) }
            schoolid?.let { tv_schoolid.text = it }
            name?.let { et_name.setText(it) }
            department?.let { et_department.setText(it) }
            speciality?.let { et_speciality.setText(it) }
            clazz?.let { et_class.setText(it) }
            qq?.let { et_qq.setText(it) }
            wechat?.let { et_wechat.setText(it) }
            phonenum?.let { et_phone.setText(it) }
        }

        iv_avatar.setOnClickListener {
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

    @SuppressLint("CheckResult")
    private fun updateInfo() {
        if (checkInfo()) {
            Api.get().userInfoUpdate(
                User.getLoggedInUser().token,
                et_username.text.toString(),
                et_name.text.toString(),
                et_department.text.toString(),
                et_speciality.text.toString(),
                et_class.text.toString(),
                et_qq.text.toString(),
                et_wechat.text.toString(),
                et_phone.text.toString()
            )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                    updateUser()
                }, {
                    Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
                })
        }
    }

    private fun checkInfo(): Boolean {
        val basicInfoList = listOf(
            et_username,
            et_name,
            et_department,
            et_speciality,
            et_class
        )

        val contactList = listOf(
            et_qq,
            et_wechat,
            et_phone
        )

        if (basicInfoList.any { it.text.isNullOrEmpty() }) {
            Toast.makeText(this, "基本信息未填写完整", Toast.LENGTH_SHORT).show()
            return false
        }

        if (contactList.all { it.text.isNullOrEmpty() }) {
            Toast.makeText(this, "请至少填写一种联系方式", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun pickImage() {
        val intent = Intent("android.intent.action.GET_CONTENT")
        intent.type = "image/*"
        startActivityForResult(intent, 0)
    }

    private var imagePath: String? = null

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            0 -> if (data != null) {
                imagePath = convertImageUriToPath(this, data)
                Glide.with(this).load(imagePath).into(iv_avatar)
                val file = File(imagePath)
                val compressedFile = CompressHelper.getDefault(applicationContext).compressToFile(file)
                val fileBody = compressedFile.asRequestBody("image/png".toMediaTypeOrNull())
                val filePart = MultipartBody.Part.createFormData("file", compressedFile.name, fileBody)
                Api.get()
                    .uploadFile(User.getLoggedInUser().token, filePart, Constants.UPLOAD_TYPE_AVATAR)
                    .subscribeOn(Schedulers.io())
                    .flatMap {
                        Api.get().userAvatarUpdate(User.getLoggedInUser().token, it.data.imgurl)
                    }
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                    }, { Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show() })

            }
        }
    }

    @SuppressLint("CheckResult")
    private fun updateUser() {
        Api.get().findUser(User.getLoggedInUser().token)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                val token = User.getLoggedInUser().token
                User.setLoggedInUser(it.user)
                User.getLoggedInUser().token = token
                if (intent.getBooleanExtra("register", false)) {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
            }, { Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show() })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.add_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
            R.id.menu_send -> updateInfo()
        }
        return super.onOptionsItemSelected(item)
    }

}
