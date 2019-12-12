package com.seven.fzuborrow.ui.notifications.statistics

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.seven.fzuborrow.R
import kotlinx.android.synthetic.main.activity_statistics.*

class StatisticsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_statistics)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

        view_pager.adapter = FragmentAdapter(this, listOf(
            BorrowSuccessFragment(),
            BorrowOutTimeFragment(),
            BorrowTimeFragment(),
            BorrowReasonFragment(),
            BorrowPeopleFragment(),
            IncreaseCreditFragment()
        ))

    }
}
