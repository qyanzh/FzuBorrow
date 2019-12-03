package com.seven.fzuborrow.ui.notifications

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.seven.fzuborrow.R
import com.seven.fzuborrow.ui.notifications.apply.ApplyHistoryActivity
import kotlinx.android.synthetic.main.fragment_notifications.view.*

class NotificationsFragment : Fragment() {

    val adapter = NotificationsMenuAdapter(
        listOf(
            NotificationsMenuItem("学校通知", R.drawable.ic_school_black_80dp),
            NotificationsMenuItem("我的申请", R.drawable.ic_send_black_80dp)
            { startActivity(Intent(context, ApplyHistoryActivity::class.java).putExtra("mode", "my_apply")) },
            NotificationsMenuItem("我借出的", R.drawable.ic_borrow_80dp)
            { startActivity(Intent(context, ApplyHistoryActivity::class.java).putExtra("mode", "my_out")) },
            NotificationsMenuItem("数据分析", R.drawable.ic_statistics_80dp)
        )
    )

    private var notificationsViewModel: NotificationsViewModel? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        notificationsViewModel = ViewModelProviders.of(this).get(
            NotificationsViewModel::class.java
        )
        val root =
            inflater.inflate(R.layout.fragment_notifications, container, false)
        root.recycler_view.adapter = adapter
        return root
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(
            TAG,
            "onDestroy: "
        )
    }

    companion object {
        private const val TAG = "NotificationsFragment"
    }
}