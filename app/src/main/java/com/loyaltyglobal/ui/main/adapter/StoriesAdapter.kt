package com.loyaltyglobal.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.loyaltyglobal.R
import com.loyaltyglobal.data.model.response.HomeScreenStoriesData
import com.loyaltyglobal.databinding.StoriesItemBinding
import com.loyaltyglobal.util.clickWithDebounce

class StoriesAdapter(
    private var storiesList: List<HomeScreenStoriesData>,
    private var clickInterface: ClickListener
) : RecyclerView.Adapter<StoriesAdapter.DataViewHolder>() {

    interface ClickListener {
        fun itemClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder =
        DataViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_stories,
                parent,
                false
            )
        )

    override fun getItemCount(): Int = storiesList.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(storiesList[position])
        holder.itemView.clickWithDebounce {
            clickInterface.itemClick(position)
        }
    }

    class DataViewHolder(var binding: StoriesItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(itemData: HomeScreenStoriesData) = binding.apply {
            mData = itemData
            executePendingBindings()
        }
    }
}
