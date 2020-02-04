package com.seven.fzuborrow.ui.notifications.statistics


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.seven.fzuborrow.R
import kotlinx.android.synthetic.main.fragment_borrow_reason.view.*

/**
 * A simple [Fragment] subclass.
 */
class BorrowReasonFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_borrow_reason, container, false)
        Glide.with(this).load(R.drawable.bg_statistic_7).into(root.imageView2)
        return root
    }


}
