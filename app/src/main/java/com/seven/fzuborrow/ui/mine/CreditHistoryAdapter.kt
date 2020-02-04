package com.seven.fzuborrow.ui.mine

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.seven.fzuborrow.R
import com.seven.fzuborrow.data.CreditHistory
import kotlinx.android.synthetic.main.item_credit_history.view.*
import java.text.SimpleDateFormat
import java.util.*

class CreditHistoryAdapter(val list: List<CreditHistory>) :
    RecyclerView.Adapter<CreditHistoryAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val reasonScore = view.tv_reason_score
        val time = view.tv_time
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_credit_history,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    val f = SimpleDateFormat("MM-dd", Locale.getDefault())

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.reasonScore.text = item.reason + (if(item.score>0) "+" else "") + item.score
        holder.time.text = f.format(item.time*1000L)
    }

}