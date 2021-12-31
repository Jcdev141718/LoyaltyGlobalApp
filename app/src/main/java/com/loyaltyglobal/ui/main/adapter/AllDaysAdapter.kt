package com.loyaltyglobal.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.loyaltyglobal.R
import com.loyaltyglobal.data.model.AllDaysModel
import com.loyaltyglobal.databinding.ItemExploreDiscountBinding

/**
 * Created by Abhin
 */

class AllDaysAdapter : ListAdapter<AllDaysModel,AllDaysAdapter.AllDaysVH>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllDaysVH {
        return AllDaysVH(DataBindingUtil.inflate(LayoutInflater.from(parent.context),
            R.layout.item_explore_discount,
            parent,
            false))
    }

    override fun onBindViewHolder(holder: AllDaysVH, position: Int) {
        holder.bind(getItem(position))
    }

    class AllDaysVH(val binding: ItemExploreDiscountBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: AllDaysModel) = binding.apply {
            data = item
            executePendingBindings()
        }
    }

    object DiffCallback : DiffUtil.ItemCallback<AllDaysModel>() {
        override fun areItemsTheSame(oldItem: AllDaysModel, newItem: AllDaysModel): Boolean {
            return oldItem.dayName == newItem.dayName
        }

        override fun areContentsTheSame(oldItem: AllDaysModel, newItem: AllDaysModel): Boolean {
            return oldItem == newItem
        }
    }
}
