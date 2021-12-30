package com.loyaltyglobal.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.loyaltyglobal.R
import com.loyaltyglobal.data.model.ExploreFilterData
import com.loyaltyglobal.databinding.ItemExploreFilterBinding
import kotlin.collections.ArrayList

/**
 * Created by Abhin.
 */
class ExploreFilterAdapter(var filterList: ArrayList<ExploreFilterData>) :
    RecyclerView.Adapter<ExploreFilterAdapter.BusinessesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BusinessesViewHolder {
        return BusinessesViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_explore_filter,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: BusinessesViewHolder, position: Int) {
        holder.bind(filterList[position])
    }

    override fun getItemCount() = filterList.size

    class BusinessesViewHolder(var itemExploreFilterBinding: ItemExploreFilterBinding) :
        RecyclerView.ViewHolder(itemExploreFilterBinding.root) {
        fun bind(card: ExploreFilterData) = itemExploreFilterBinding.apply {
            data = card
            executePendingBindings()
        }
    }
}
