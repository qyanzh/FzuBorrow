package com.seven.fzuborrow.ui.community.detail

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.seven.fzuborrow.R
import com.seven.fzuborrow.data.User
import com.seven.fzuborrow.network.Api
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_disc_detail.*
import kotlinx.android.synthetic.main.dialog_comment.*

class DiscDetailActivity : AppCompatActivity() {

    var discId = -1
    val adapter = DiscCommentAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_disc_detail)
        setSupportActionBar(toolbar)
        supportActionBar?.apply { title = "";setDisplayHomeAsUpEnabled(true) }
        discId = intent.getIntExtra("discId", -1)
        rv_comments.adapter = adapter
        refresh()
    }

    @SuppressLint("CheckResult")
    private fun refresh() {
        Api.get().findAllComments(User.getLoggedInUser().token, discId.toLong())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response -> adapter.submitList(response.commentList) },
                { e -> e.printStackTrace() })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.add_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
            R.id.menu_send -> showCommentDialog()
        }
        return true
    }

    private fun showCommentDialog() {
        val dialog = MaterialAlertDialogBuilder(this)
            .setTitle("发表回复")
            .setView(R.layout.dialog_comment)
            .setPositiveButton("发表", null)
            .setNegativeButton("取消", null)
            .create()
        dialog.show()
        dialog.apply {
            getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener {
                Api.get().publishComment(
                    User.getLoggedInUser().token, discId.toLong(), User.getLoggedInUser().username,
                    dialog.et_comment.text.toString()
                ).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ r ->
                        run {
                            Toast.makeText(
                                this@DiscDetailActivity,
                                "发表成功",
                                Toast.LENGTH_SHORT
                            ).show()
                            refresh()
                            dismiss()
                        }
                    }, { e -> e.printStackTrace() })
            }
            getButton(DialogInterface.BUTTON_NEGATIVE).setOnClickListener { dismiss() }
        }
    }
}
