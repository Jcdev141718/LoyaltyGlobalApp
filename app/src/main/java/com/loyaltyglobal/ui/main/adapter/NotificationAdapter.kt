package com.loyaltyglobal.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.loyaltyglobal.data.source.localModels.NotificationAndSubBrand
import com.loyaltyglobal.databinding.ItemNotificationBinding


/**
 * Created by Abhin.
 */

class NotificationAdapter(private val onNotificationClick: OnNotificationClick) :
    ListAdapter<NotificationAndSubBrand, NotificationAdapter.ItemViewHolder>(DiffCallback()) {

    interface OnNotificationClick {
        fun onItemClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(ItemNotificationBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener {
            onNotificationClick.onItemClick(position)
        }
    }

    class ItemViewHolder(private val binding: ItemNotificationBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(itemData: NotificationAndSubBrand) = binding.apply {
            data = itemData
            executePendingBindings()
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<NotificationAndSubBrand>() {
        override fun areItemsTheSame(
            oldItem: NotificationAndSubBrand,
            newItem: NotificationAndSubBrand,
        ): Boolean {
            return oldItem.notification._id == newItem.notification._id
        }

        override fun areContentsTheSame(
            oldItem: NotificationAndSubBrand,
            newItem: NotificationAndSubBrand,
        ): Boolean {
            return oldItem == newItem
        }
    }
}
