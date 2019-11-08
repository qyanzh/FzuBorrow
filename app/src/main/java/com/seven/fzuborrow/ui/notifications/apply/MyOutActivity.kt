package com.seven.fzuborrow.ui.notifications.apply

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import com.seven.fzuborrow.R
import kotlinx.android.synthetic.main.activity_my_out.*

class MyOutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_out)
        toolbar.title = "我的借出"
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val fragmentList = listOf(
            ApplyFragment.newInstance(arrayListOf())
            , ApplyFragment.newInstance(arrayListOf()),
            ApplyFragment.newInstance(arrayListOf())
        )
        view_pager.adapter = object :
            FragmentPagerAdapter(supportFragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
            override fun getItem(position: Int): Fragment {
                return fragmentList[position]
            }

            override fun getCount(): Int {
                return fragmentList.size
            }

            override fun getPageTitle(position: Int): CharSequence? {
                return when (position) {
                    0 -> "未处理"
                    1 -> "已归还"
                    2 -> "已拒绝"
                    else -> ""
                }
            }
        }
        view_pager.offscreenPageLimit = 2
        tab_layout.setupWithViewPager(view_pager)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return true
    }
}
