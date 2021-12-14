package com.loyaltyglobal.ui.main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loyaltyglobal.data.model.NotificationData

/**
 * Created by Abhin.
 */
class NotificationViewModel : ViewModel() {

    var mutableNotificationList = MutableLiveData<ArrayList<NotificationData>>()

    fun getNotificationList() {
        val list = ArrayList<NotificationData>()
        list.add(
            NotificationData(
                title = "Mcdonalds has updated 20 points.",
                sub_title = "30 minutes ago",
                is_notification_read = true
            )
        )
        list.add(
            NotificationData(
                title = "KFC a new brand has been added.",
                sub_title = "30 minutes ago",
                is_notification_read = true
            )
        )
        list.add(
            NotificationData(
                title = "Burger King added their story: “A MEGA DEAL TILL 10 Nov, 2021”",
                sub_title = "30 minutes ago",
                is_notification_read = true
            )
        )
        list.add(
            NotificationData(
                title = "Mcdonalds has updated 20 points.",
                sub_title = "30 minutes ago"
            )
        )
        list.add(
            NotificationData(
                title = "Mcdonalds has updated 20 points.",
                sub_title = "30 minutes ago"
            )
        )
        mutableNotificationList.value = list
    }
}
