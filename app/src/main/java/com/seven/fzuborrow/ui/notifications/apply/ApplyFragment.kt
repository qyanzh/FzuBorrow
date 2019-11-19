package com.seven.fzuborrow.ui.notifications.apply


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.seven.fzuborrow.R
import com.seven.fzuborrow.data.Apply
import com.seven.fzuborrow.data.User
import com.seven.fzuborrow.network.Api
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class ApplyFragment : Fragment() {

    companion object {
        fun newInstance(applies: ArrayList<Apply>): ApplyFragment {
            val fragment = ApplyFragment()
            val bundle = Bundle()
            bundle.putParcelableArrayList("applies", applies)
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
            Toast.makeText(context, apply.rid.toString(), Toast.LENGTH_SHORT).show()
        })

        (recyclerView.adapter as ApplyAdapter).submitList(arguments!!["applies"] as List<Apply>)

        // Inflate the layout for this fragment
        return recyclerView
    }


}
