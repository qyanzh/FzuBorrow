package com.seven.fzuborrow.ui.notifications.apply


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.seven.fzuborrow.Constants
import com.seven.fzuborrow.R
import com.seven.fzuborrow.data.Apply
import com.seven.fzuborrow.data.User
import com.seven.fzuborrow.network.Api
import com.seven.fzuborrow.ui.mine.restore.ConfirmRestoreActivity
import com.seven.fzuborrow.ui.notifications.apply.detail.ApplyDetailActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class ApplyFragment : Fragment() {

    companion object {
        fun newInstance(applies: ArrayList<Apply>,isOut:Boolean): ApplyFragment {
            val fragment = ApplyFragment()
            val bundle = Bundle()
            bundle.putParcelableArrayList("applies", applies)
            bundle.putBoolean("isOut", isOut)
            fragment.arguments = bundle
            return fragment
        }
    }

    @SuppressLint("CheckResult")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val recyclerView =
            inflater.inflate(R.layout.fragment_apply, container, false) as RecyclerView
        recyclerView.adapter = ApplyAdapter(ApplyListener { apply ->
            if(apply.status == Constants.APPLY_STATUS_WAITING) {
                startActivity(Intent(context,ConfirmRestoreActivity::class.java).putExtra("apply",apply))
            } else {
                startActivity(Intent(context,ApplyDetailActivity::class.java).putExtra("apply",apply))
            }
        },arguments!!.getBoolean("isOut"))
        recyclerView.setHasFixedSize(true)

        (recyclerView.adapter as ApplyAdapter).submitList(arguments!!["applies"] as List<Apply>)

        // Inflate the layout for this fragment
        return recyclerView
    }


}
