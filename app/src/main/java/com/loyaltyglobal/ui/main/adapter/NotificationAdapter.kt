package com.loyaltyglobal.ui.main.adapter

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.loyaltyglobal.data.source.localModels.NotificationAndSubBrand
import com.loyaltyglobal.databinding.ItemNotificationBinding


/**
 * Created by Abhin.
 */

class NotificationAdapter(private val onNotificationClick: OnNotificationClick) : ListAdapter<NotificationAndSubBrand, NotificationAdapter.ItemViewHolder>(DiffCallback()) {

    interface OnNotificationClick{
        fun onItemClick(itemData: NotificationAndSubBrand)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(ItemNotificationBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(getItem(position),onNotificationClick)
    }

    class ItemViewHolder(private val binding: ItemNotificationBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(itemData: NotificationAndSubBrand, onNotificationClick: OnNotificationClick) = binding.apply {
            data = itemData
            executePendingBindings()
            itemView.setOnClickListener{
                onNotificationClick.onItemClick(itemData)
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<NotificationAndSubBrand>() {
        override fun areItemsTheSame(
            oldItem: NotificationAndSubBrand,
            newItem: NotificationAndSubBrand
        ): Boolean {
            return oldItem.notification.title == newItem.notification.title
        }

        override fun areContentsTheSame(
            oldItem: NotificationAndSubBrand,
            newItem: NotificationAndSubBrand
        ): Boolean {
            return oldItem == newItem
        }
    }
}
