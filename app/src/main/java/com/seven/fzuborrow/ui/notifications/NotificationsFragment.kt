package com.seven.fzuborrow.ui.notifications

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_CANCEL_CURRENT
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.NotificationCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.seven.fzuborrow.Constants
import com.seven.fzuborrow.R
import com.seven.fzuborrow.data.User
import com.seven.fzuborrow.network.Api
import com.seven.fzuborrow.ui.notifications.apply.ApplyHistoryActivity
import kotlinx.android.synthetic.main.fragment_notifications.view.*
import java.lang.Thread.sleep


class NotificationsFragment : Fragment() {


    val list = listOf(
        NotificationsMenuItem("学校通知", R.drawable.ic_school_black_80dp),
        NotificationsMenuItem("我的申请", R.drawable.ic_send_black_80dp)
        {
            startActivity(
                Intent(context, ApplyHistoryActivity::class.java).putExtra(
                    "mode",
                    "my_apply"
                )
            )
        },
        NotificationsMenuItem("我借出的", R.drawable.ic_borrow_80dp)
        {
            startActivity(
                Intent(context, ApplyHistoryActivity::class.java).putExtra(
                    "mode",
                    "my_out"
                )
            )
        },
        NotificationsMenuItem("数据分析", R.drawable.ic_statistics_80dp)
    )
    val adapter = NotificationsMenuAdapter(list)

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
        getNotification()
        return root
    }

    var alive = true
    override fun onDestroy() {
        super.onDestroy()
        alive = false
    }

    override fun onResume() {
        super.onResume()
        alive = true
    }

    override fun onPause() {
        super.onPause()
        alive = false
    }

    @SuppressLint("CheckResult")
    private fun getNotification() {
        val spf = activity?.getSharedPreferences("notification", 0)!!
        Thread {
            while (alive) {
                Api.get().findBeApply(User.getLoggedInUser().token).subscribe({
                    var readed = spf.getString("readed_be_apply", "")!!.run {
                        if (this.isBlank()) {
                            listOf()
                        } else {
                            this.split(" ").map { it.toInt() }
                        }
                    }
                    val pendding =
                        it.applyList.filter { it.status == Constants.APPLY_STATUS_PENDING }
                            .map { it.pid }
                    val unreaded = pendding - readed
                    if (unreaded.isNotEmpty()) {
                        sendNotification(unreaded, "be_apply")
                    }
                    readed = (readed + unreaded).toMutableList()
                    spf.edit().putString("readed_be_apply", readed.joinToString { " " }).apply()
                }, { e -> e.printStackTrace() })
                sleep(5000)
            }
        }.start()

        Thread {
            while (alive) {
                Api.get().findApply(User.getLoggedInUser().token).subscribe({
                    var readed = spf.getString("readed_apply", "")!!.run {
                        if (this.isBlank()) {
                            listOf()
                        } else {
                            this.split(" ").map { it.toInt() }
                        }
                    }
                    val pendding =
                        it.applyList.filter { it.status == Constants.APPLY_STATUS_USING || it.status == Constants.APPLY_STATUS_REJECTED }
                            .map { it.pid }
                    val unreaded = pendding - readed
                    if (unreaded.isNotEmpty()) {
                        sendNotification(unreaded, "apply")
                    }
                    readed = (readed + unreaded).toMutableList()
                    spf.edit().putString("readed_apply", readed.joinToString { " " }).apply()
                }, { e -> e.printStackTrace() })
                sleep(5000)
            }
        }.start()
    }

    private fun sendNotification(unreaded: List<Int>, type: String) {
        if (type == "be_apply") {
            list[2].notificationNums = unreaded.size
            showNotification("有新的租借请求", "点击查看")
        } else {
            list[1].notificationNums = unreaded.size
        }
        adapter.notifyDataSetChanged()
    }

    fun showNotification(title: String, detail: String) {
        val manager =
            activity!!.getSystemService(NOTIFICATION_SERVICE) as NotificationManager?
        val notification: Notification = NotificationCompat.Builder(context!!, "10086")
            .setContentTitle(title)
            .setContentText(detail)
            .setWhen(System.currentTimeMillis())
            .setSmallIcon(R.mipmap.ic_launcher_foreground)
            .setLargeIcon(BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher))
            .setAutoCancel(true)
            .setContentIntent(
                PendingIntent.getActivity(
                    context!!, 0, Intent(activity!!, ApplyHistoryActivity::class.java).putExtra(
                        "mode",
                        "my_out"
                    ), FLAG_CANCEL_CURRENT
                )
            )
            .build()
        manager!!.notify(100, notification)
    }
}