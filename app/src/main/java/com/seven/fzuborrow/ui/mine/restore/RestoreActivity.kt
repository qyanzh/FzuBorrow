package com.seven.fzuborrow.ui.mine.restore

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.seven.fzuborrow.R
import com.seven.fzuborrow.data.Apply
import com.seven.fzuborrow.data.User
import com.seven.fzuborrow.network.Api
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_restore.*
import kotlin.math.roundToInt

class RestoreActivity : AppCompatActivity() {

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restore)

        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = ""
        }

        bt_return.setOnClickListener {
            val rating = rating_bar.rating.roundToInt()
            Api.get()
                .returnGood(
                    User.getLoggedInUser().token,
                    intent.getParcelableExtra<Apply>("apply")!!.rid.toLong(),
                    rating
                )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                    bt_return.visibility = View.GONE
                }, { Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show() })
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return true
    }
}
