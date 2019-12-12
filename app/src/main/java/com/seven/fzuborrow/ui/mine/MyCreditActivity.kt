package com.seven.fzuborrow.ui.mine

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.seven.fzuborrow.R
import com.seven.fzuborrow.data.User
import com.seven.fzuborrow.network.Api
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_my_credit.*
import retrofit2.HttpException

class MyCreditActivity : AppCompatActivity() {

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_credit)
        dv_credit.setCreditValueWithAnim(User.getLoggedInUser().credit)
        Api.get().getCreditRecord(User.getLoggedInUser().token)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                rv_credit_history.adapter = CreditHistoryAdapter(it.data)
            }, { e ->
                if (e is HttpException) {
                    Toast.makeText(this, e.response()!!.errorBody()!!.string(), Toast.LENGTH_SHORT)
                        .show()
                } else {
                    Toast.makeText(
                        this
                        , "网络异常", Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }
}
