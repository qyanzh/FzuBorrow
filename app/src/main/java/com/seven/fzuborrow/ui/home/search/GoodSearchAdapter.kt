package com.seven.fzuborrow.ui.home.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.seven.fzuborrow.Constants
import com.seven.fzuborrow.R
import com.seven.fzuborrow.data.Good
import kotlinx.android.synthetic.main.good_search_item.view.*

class GoodSearchAdapter(val listener: GoodListener) :
    ListAdapter<Good, RecyclerView.ViewHolder>(ApplyDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)!!.inflate(
                R.layout.good_search_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val good = getItem(position)
        holder.itemView.apply {
            if(good.imgurl!=null) {
                Glide.with(this).load(good.imgurl).into(iv_image)
            }
            tv_good_name.text = good.name
            tv_good_status.text = when (good.status) {
                Constants.GOOD_STATUS_AVAILABLE -> "空闲中"
                Constants.GOOD_STATUS_BORROWED -> "已借出"
                else -> "未知"
            }
            setOnClickListener {
                listener.onClick(good)
            }
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
}

class ApplyDiffCallback : DiffUtil.ItemCallback<Good>() {
    override fun areItemsTheSame(oldItem: Good, newItem: Good): Boolean {
        return oldItem.gid == newItem.gid
    }

    override fun areContentsTheSame(oldItem: Good, newItem: Good): Boolean {
        return oldItem.gid == newItem.gid
    }
}

class GoodListener(val listener: (good: Good) -> Unit) {
    fun onClick(good: Good) {
        listener(good)
    }
}