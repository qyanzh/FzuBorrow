package com.seven.fzuborrow.ui.notifications.apply

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.seven.fzuborrow.R
import com.seven.fzuborrow.data.Apply
import kotlinx.android.synthetic.main.apply_item.view.*

class ApplyAdapter(val listener:ApplyListener) :
    ListAdapter<Apply, RecyclerView.ViewHolder>(ApplyDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)!!.inflate(
                R.layout.apply_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val apply = getItem(position)
        holder.itemView.apply {
            tv_username.text = apply.uid.toString()
            tv_message.text = apply.reason
            bt_more.apply {
                setOnClickListener {
                    listener.onClick(apply)
                }
                bt_more.text = apply.status.toString()
            }
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
}

class ApplyDiffCallback : DiffUtil.ItemCallback<Apply>() {
    override fun areItemsTheSame(oldItem: Apply, newItem: Apply): Boolean {
        return oldItem.rid == newItem.rid
    }

    override fun areContentsTheSame(oldItem: Apply, newItem: Apply): Boolean {
        return oldItem.rid == newItem.rid
    }
}

class ApplyListener(val listener:(apply:Apply)->Unit) {
    fun onClick(apply: Apply) {
        listener(apply)
    }
}