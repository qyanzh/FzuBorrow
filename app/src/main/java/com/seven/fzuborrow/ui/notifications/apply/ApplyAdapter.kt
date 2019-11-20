package com.seven.fzuborrow.ui.notifications.apply

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.collection.LongSparseArray
import androidx.collection.SparseArrayCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.seven.fzuborrow.R
import com.seven.fzuborrow.data.Apply
import com.seven.fzuborrow.data.User
import com.seven.fzuborrow.network.Api
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.apply_item.view.*

class ApplyAdapter(val listener:ApplyListener) :
    ListAdapter<Apply, RecyclerView.ViewHolder>(ApplyDiffCallback()) {
    val map = SparseArrayCompat<User>()
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
            if (map.containsKey(apply.uid)) {
                val user = map[apply.uid]
                tv_username.text = user?.username
                Glide.with(this).load(user?.imgurl)
            } else {
                //TODO:查询用户
                Api.get().findUser(User.getLoggedInUser().token,apply.uid.toLong()).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe ({
                        map.put(apply.uid,it.user)
                        val user = map[apply.uid]
                        tv_username.text = user?.username
                        Glide.with(this).load(user?.imgurl)
                    },{ Toast.makeText(this.context, "网络连接异常", Toast.LENGTH_SHORT).show()})
            }
            tv_message.text = apply.reason
            bt_more.apply {
                setOnClickListener {
                    listener.onClick(apply)
                }
                bt_more.text = "查看详情 >"
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