package com.seven.fzuborrow.ui.notifications

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.seven.fzuborrow.R
import kotlinx.android.synthetic.main.item_notification_menu.view.*

class NotificationsMenuAdapter(val list: List<NotificationsMenuItem>) :
    RecyclerView.Adapter<NotificationsMenuAdapter.ViewHolder>() {

    lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_notification_menu,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.itemView.setOnClickListener { item.clickListener.invoke() }
        holder.icon.setImageDrawable(context.getDrawable(item.icon))
        holder.text.text = item.name
        if(item.notificationNums == 0) {
            holder.flag.visibility = View.INVISIBLE
        } else {
            holder.flag.visibility = View.VISIBLE
            holder.num.text = item.notificationNums.toString()
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val icon = view.noti_menu_icon
        val text = view.noti_menu_text
        val num = view.flag_num
        val flag = view.flag
    }
}