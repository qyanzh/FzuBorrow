package com.seven.fzuborrow.ui.home.search

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayout
import com.seven.fzuborrow.R
import com.seven.fzuborrow.data.User
import com.seven.fzuborrow.network.Api
import com.seven.fzuborrow.ui.home.detail.GoodDetailActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_good_search.*

class GoodSearchActivity : AppCompatActivity() {

    private var type = "good"
    private lateinit var adapter: GoodSearchAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_good_search)
        bt_cancel.setOnClickListener {
            onBackPressed()
        }
        adapter = GoodSearchAdapter(GoodListener { good ->
            startActivity(
                Intent(this, GoodDetailActivity::class.java).putExtra(
                    "good", good
                )
            )
        })
        recycler_view.adapter = adapter
        et_search_content.requestFocus()
        tab_layout.addTab(tab_layout.newTab().setText("物品"))
        tab_layout.addTab(tab_layout.newTab().setText("活动室"))
        tab_layout.setOnTabSelectedListener(object :
            TabLayout.BaseOnTabSelectedListener<TabLayout.Tab> {
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                type = when (tab?.text) {
                    "活动室" -> "room"
                    else -> "good"
                }
                refresh(et_search_content.text)
            }
        })
        et_search_content.addTextChangedListener(object : TextWatcher {
            @SuppressLint("CheckResult")
            override fun afterTextChanged(s: Editable?) {
                if (s.toString().isNotBlank()) {
                    refresh(s)
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })
    }

    @SuppressLint("CheckResult")
    fun refresh(query: Editable?) {
        if (query.toString().isNotBlank()) {
            Api.get().findAllGood(User.getLoggedInUser().token, type, query.toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    adapter.submitList(it.goodList)
                }, { Toast.makeText(this, "网络连接异常", Toast.LENGTH_SHORT).show() })
        }
    }
}
