package com.loyaltyglobal.notifications

/**
 * Created by Abhin.
 */
interface NotificationReceiveListener {
    fun onNotificationReceive(payload : String)
}