package com.loyaltyglobal.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.loyaltyglobal.R
import com.loyaltyglobal.data.model.NotificationData
import com.loyaltyglobal.databinding.ItemNotificationBinding

/**
 * Created by Abhin.
 */
class NotificationAdapter(var notificationList: ArrayList<NotificationData>) :
    RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        return NotificationViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_notification,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        holder.bind(notificationList[position])
    }

    override fun getItemCount() = notificationList.size

    class NotificationViewHolder(var itemCardListBinding: ItemNotificationBinding) :
        RecyclerView.ViewHolder(itemCardListBinding.root) {
        fun bind(notificationData: NotificationData) = itemCardListBinding.apply {
            data = notificationData
            executePendingBindings()
        }
    }
}
