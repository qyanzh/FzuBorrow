package com.seven.fzuborrow.ui.notifications.apply

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import com.seven.fzuborrow.Constants.*
import com.seven.fzuborrow.R
import com.seven.fzuborrow.data.Apply
import com.seven.fzuborrow.data.User
import com.seven.fzuborrow.network.Api
import com.seven.fzuborrow.network.response.FindApplyResponse
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_apply_history.*

class ApplyHistoryActivity : AppCompatActivity() {

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_apply_history)
        var observable: Observable<FindApplyResponse>
        if (intent.getStringExtra("mode") == "my_apply") {
            toolbar.title = "我的申请"
            observable = Api.get().findApply(User.getLoggedInUser().token)
        } else {
            toolbar.title = "我的借出"
            observable = Api.get().findBeApply(User.getLoggedInUser().token)
        }
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        observable
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                val fragmentList = listOf(
                    ApplyFragment.newInstance(it.applyList.filter { it.status == APPLY_STATUS_PENDDING } as ArrayList<Apply>),
                    ApplyFragment.newInstance(it.applyList.filter { it.status == APPLY_STATUS_USING } as ArrayList<Apply>),
                    ApplyFragment.newInstance(it.applyList.filter { it.status == APPLY_STATUS_FINISHED } as ArrayList<Apply>)
                )
                view_pager.adapter = object :
                    FragmentPagerAdapter(
                        supportFragmentManager,
                        BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
                    ) {
                    override fun getItem(position: Int): Fragment {
                        return fragmentList[position]
                    }

                    override fun getCount(): Int {
                        return fragmentList.size
                    }

                    override fun getPageTitle(position: Int): CharSequence? {
                        return when (position) {
                            0 -> "未处理"
                            1 -> "使用中"
                            2 -> "已完成"
                            else -> ""
                        }
                    }
                }
                view_pager.offscreenPageLimit = 2
                tab_layout.setupWithViewPager(view_pager)
            }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return true
    }
}
