package com.seven.fzuborrow.ui.notifications

class NotificationsMenuItem(
    val name: String,
    val icon: Int,
    var clickListener: () -> Unit = {}
) {
    var notificationNums = 0
}