package com.loyaltyglobal.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.loyaltyglobal.R
import com.loyaltyglobal.data.source.localModels.FilterModel
import com.loyaltyglobal.databinding.ItemExploreFilterBinding

/**
 * Created by Abhin.
 */
class ExploreFilterAdapter(private var listener: FilterCallback) :
    ListAdapter<FilterModel,ExploreFilterAdapter.BusinessesViewHolder>(DiffCallback) {

    interface FilterCallback {
        fun onFilter(position: Int, isChecked: Boolean)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BusinessesViewHolder {
        return BusinessesViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context),
            R.layout.item_explore_filter,
            parent,
            false))
    }

    override fun onBindViewHolder(holder: BusinessesViewHolder, position: Int) {
        holder.itemExploreFilterBinding.filterCheckbox.setOnCheckedChangeListener { buttonView, isChecked ->
            if (buttonView.isPressed){
                listener.onFilter(position, isChecked)
            }
        }
        holder.bind(getItem(position))
    }

    class BusinessesViewHolder(var itemExploreFilterBinding: ItemExploreFilterBinding) :
        RecyclerView.ViewHolder(itemExploreFilterBinding.root) {
        fun bind(card: FilterModel) = itemExploreFilterBinding.apply {
            data = card
            executePendingBindings()
        }
    }

    object DiffCallback : DiffUtil.ItemCallback<FilterModel>() {
        override fun areItemsTheSame(oldItem: FilterModel, newItem: FilterModel): Boolean {
            return oldItem.filterTitle == oldItem.filterTitle
        }

        override fun areContentsTheSame(oldItem: FilterModel, newItem: FilterModel): Boolean {
            return oldItem == newItem
        }
    }
}
