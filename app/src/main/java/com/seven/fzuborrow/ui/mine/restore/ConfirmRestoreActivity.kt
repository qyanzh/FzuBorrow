package com.seven.fzuborrow.ui.mine.restore

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.seven.fzuborrow.Constants
import com.seven.fzuborrow.R
import com.seven.fzuborrow.data.Apply
import com.seven.fzuborrow.data.Good
import com.seven.fzuborrow.data.User
import com.seven.fzuborrow.network.Api
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_confirm_restore.*
import kotlinx.android.synthetic.main.activity_restore.*
import kotlinx.android.synthetic.main.activity_restore.toolbar

class ConfirmRestoreActivity : AppCompatActivity() {

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirm_restore)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = ""
        }
        val apply = intent.getParcelableExtra<Apply>("apply")!!
        if(apply.pid.toLong() != User.getLoggedInUser().uid) {
            bt_confirm_return.visibility = View.GONE
        }

        bt_confirm_return.setOnClickListener {
            Api.get()
                .confirmReturn(User.getLoggedInUser().token, apply.rid.toLong(),Constants.CONFIRM_TYPE_OK)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                    bt_confirm_return.visibility = View.GONE
                },{ Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()})
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return true
    }
}
