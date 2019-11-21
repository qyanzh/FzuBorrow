package com.seven.fzuborrow.ui.mine.restore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.seven.fzuborrow.R
import com.seven.fzuborrow.data.Good
import com.seven.fzuborrow.data.User
import com.seven.fzuborrow.network.Api
import kotlinx.android.synthetic.main.activity_restore.*

class RestoreActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restore)

        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = ""
        }

        Api.get().returnGood(User.getLoggedInUser().token,intent.getParcelableExtra<Good>("good").gid)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return true
    }
}
