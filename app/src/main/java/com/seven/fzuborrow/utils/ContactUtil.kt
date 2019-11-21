package com.seven.fzuborrow.utils

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.seven.fzuborrow.R
import com.seven.fzuborrow.data.User

private fun copyWechat(context:Context,wechat:String) {
    (context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager)
        .setPrimaryClip(ClipData.newPlainText("wechat", wechat))
    Toast.makeText(context, "已复制到剪切板", Toast.LENGTH_SHORT).show()
}

private fun jumpToQQ(context:Context,qq:String) {
    try {
        val url = "mqqwpa://im/chat?chat_type=wpa&uin=${qq}"
        context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    } catch (e: Exception) {
        (context?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager)
            .setPrimaryClip(ClipData.newPlainText("phone", qq))
        Toast.makeText(context, "打开QQ失败, 已复制到剪切板", Toast.LENGTH_SHORT)
            .show()
    }
}

private fun jumpToPhone(context:Context,phone:String) {
    try {
        val intent =
            Intent(Intent.ACTION_DIAL, Uri.parse("tel:${phone}"))
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent)
    } catch (e: Exception) {
        (context?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager)
            .setPrimaryClip(ClipData.newPlainText("phone", phone))
        Toast.makeText(context, "打开拨号应用失败, 已复制到剪切板", Toast.LENGTH_SHORT)
            .show()
    }
}

public fun showContactDialog(context:Context,user: User) {
    val contactList = mutableListOf<String>()
    user.qq?.let {
        contactList.add("QQ: $it (点击跳转)")
    }
    user?.wechat?.let {
        contactList.add("微信: $it (点击复制)")
    }
    user?.phonenum?.let {
        contactList.add("手机: $it (点击跳转)")
    }
    MaterialAlertDialogBuilder(context, R.style.AlertDialogTheme)
        .setTitle("车主联系方式")
        .setItems(contactList.toTypedArray()) { _, position ->
            when {
                contactList[position].startsWith("微信") -> copyWechat(context,user.wechat)
                contactList[position].startsWith("QQ") -> jumpToQQ(context,user.qq)
                contactList[position].startsWith("手机") -> jumpToPhone(context,user.phonenum)
            }
        }.show()
}