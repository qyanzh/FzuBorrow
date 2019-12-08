package com.seven.fzuborrow.ui.community.detail

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.collection.LongSparseArray
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.seven.fzuborrow.R
import com.seven.fzuborrow.data.Comment
import com.seven.fzuborrow.data.User
import com.seven.fzuborrow.network.Api
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.item_comment.view.*
import java.text.SimpleDateFormat
import java.util.*

class DiscCommentAdapter : ListAdapter<Comment, DiscCommentAdapter.ViewHolder>(object :
    DiffUtil.ItemCallback<Comment>() {
    override fun areItemsTheSame(oldItem: Comment, newItem: Comment): Boolean {
        return oldItem.cid == newItem.cid
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: Comment, newItem: Comment): Boolean {
        return oldItem == newItem
    }

}) {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val avatar = view.iv_avatar
        val username = view.tv_username
        val content = view.tv_content
        val time = view.tv_time
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_comment,
                parent,
                false
            )
        )
    }

    @SuppressLint("CheckResult")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val comment = currentList[position]
        var avatar: String
        if (users.containsKey(comment.userId)) {
            avatar = users[comment.userId]!!.imgurl
            Glide.with(holder.itemView).load(avatar).into(holder.avatar)
            holder.username.text = users[comment.userId]!!.username
            holder.content.text = comment.content
            holder.time.text = f.format(Date(comment.ctime*1000))
        } else {
            Api.get().findUserByUid(User.getLoggedInUser().token, comment.userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ u ->
                    run {
                        users.put(comment.userId, u.user)
                        avatar = users[comment.userId]!!.imgurl
                        Glide.with(holder.itemView).load(avatar).into(holder.avatar)
                        holder.username.text = users[comment.userId]!!.username
                        holder.content.text = comment.content
                        holder.time.text = f.format(Date(comment.ctime*1000))
                    }
                }, { e -> e.printStackTrace() })
        }
    }

    val users = LongSparseArray<User>()
    val f = SimpleDateFormat("MM-dd HH:mm", Locale.getDefault())
}