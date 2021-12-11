package com.loyaltyglobal.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView

import com.loyaltyglobal.R
import com.loyaltyglobal.databinding.ItemStoriesBinding
import com.loyaltyglobal.util.clickWithDebounce

class StoriesAdapter(
    private var storiesList: ArrayList<String>,
    private var clickInterface : ClickListener
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
        holder.binding.imgStories.clickWithDebounce {
            clickInterface.itemClick(position)
        }
    }

    class DataViewHolder(var binding: ItemStoriesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(itemData: String) = binding.apply {
           // mData = itemData
           // executePendingBindings()
        }
    }

    fun setData(list: List<String>, loadMore: Boolean) {
        val oldListSize = this.storiesList.size
        if (!loadMore) {
            storiesList.clear()
            storiesList.addAll(list)
        } else {
            storiesList.addAll(list)
        }
        notifyItemRangeChanged(oldListSize, list.size)
    }


}
