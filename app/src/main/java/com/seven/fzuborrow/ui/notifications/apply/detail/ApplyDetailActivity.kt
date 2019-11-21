package com.seven.fzuborrow.ui.notifications.apply.detail

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.seven.fzuborrow.R
import com.seven.fzuborrow.data.Apply
import com.seven.fzuborrow.data.User
import com.seven.fzuborrow.network.Api
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_apply_detail.*
import java.text.SimpleDateFormat
import java.util.*

class ApplyDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_apply_detail)

        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            title = ""
            setDisplayHomeAsUpEnabled(true)
        }

        val apply = intent.getParcelableExtra<Apply>("apply") ?: return
        //TODO 查询用户
        Api.get().findUser(User.getLoggedInUser().token, apply.uid.toLong())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext {
                tv_username.text = it.user.username
                Glide.with(this).load(it.user.imgurl).into(iv_avatar)
                tv_name.text = it.user.name
                tv_department.text = it.user.department
                tv_class.text = it.user.speciality + it.user.clazz
            }.doOnError {
                it.printStackTrace()
                Toast.makeText(this, "网络异常", Toast.LENGTH_SHORT).show()
            }
            .subscribe()

        Api.get().findGood(User.getLoggedInUser().token, apply.gid.toLong())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext {
                tv_good.text = it.good.name
                val f = SimpleDateFormat("MM/dd HH:mm", Locale.getDefault())
                tv_time.text = f.format(apply.startTime) + " - " + f.format(apply.endTime)
                tv_reason.text = apply.reason
            }
            .doOnError {
                it.printStackTrace()
                Toast.makeText(this, "网络异常", Toast.LENGTH_SHORT).show()
            }.subscribe()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return true
    }
}
