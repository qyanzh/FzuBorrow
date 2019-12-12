package com.seven.fzuborrow.network.response

import com.seven.fzuborrow.data.CreditHistory

data class CreditRecordResponse(
    val code:Int,
    val message:String,
    val data:List<CreditHistory>
) {
}