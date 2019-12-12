package com.seven.fzuborrow.ui.mine.restore

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.RatingBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.seven.fzuborrow.Constants
import com.seven.fzuborrow.R
import com.seven.fzuborrow.data.Apply
import com.seven.fzuborrow.data.User
import com.seven.fzuborrow.network.Api
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_confirm_restore.*
import kotlinx.android.synthetic.main.activity_restore.toolbar
import kotlinx.android.synthetic.main.dialog_rating.*
import kotlinx.android.synthetic.main.dialog_rating.view.*
import kotlin.math.roundToInt

class ConfirmRestoreActivity : AppCompatActivity() {
    lateinit var apply: Apply
    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirm_restore)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = ""
        }
        apply = intent.getParcelableExtra<Apply>("apply")!!
        if (apply.pid.toLong() != User.getLoggedInUser().uid) {
            bt_confirm_return.visibility = View.GONE
        }

        bt_confirm_return.setOnClickListener {
            showRatingDialog()
        }

    }

    private fun showRatingDialog() {
        val dialog = MaterialAlertDialogBuilder(this)
            .setTitle("给对方评个星吧")
            .setView(R.layout.dialog_rating)
            .setPositiveButton("确认",null)
            .setNegativeButton("取消",null)
            .setCancelable(false).create()
        dialog.show()
        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener {
            val rating = dialog.dialog_rating_bar.rating.roundToInt()
            confirmReturn(rating)
        }
        dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setOnClickListener { dialog.dismiss() }
    }

    @SuppressLint("CheckResult")
    private fun confirmReturn(rating:Int) {
        Api.get()
            .confirmReturn(
                User.getLoggedInUser().token,
                apply.rid.toLong(),
                Constants.CONFIRM_TYPE_OK,
                rating
            )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                bt_confirm_return.visibility = View.GONE
                finish()
            }, { Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show() })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return true
    }
}
