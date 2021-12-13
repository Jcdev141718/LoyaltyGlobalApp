package com.loyaltyglobal.ui.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.updateLayoutParams
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.loyaltyglobal.R
import com.loyaltyglobal.data.model.response.HomeScreenStoriesData
import com.loyaltyglobal.databinding.HomeScreenStoriesItemBinding
import com.loyaltyglobal.util.setFrameMargin
import kotlin.math.roundToInt

/**
 * Created by Abhin.
 */
class HomeScreenStoriesAdapter(
    private var context: Context,
    private var mList: List<HomeScreenStoriesData>,
    private val mItemClickListener: ItemClickListener
) : RecyclerView.Adapter<HomeScreenStoriesAdapter.CommonAdapterViewHolder>() {

    var binding: HomeScreenStoriesItemBinding? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommonAdapterViewHolder {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_home_screen_stories,
            parent,
            false
        )
        return CommonAdapterViewHolder(binding!!)
    }

    override fun getItemCount() = mList.size

    override fun onBindViewHolder(holder: CommonAdapterViewHolder, position: Int) {
        val banner = mList[position]
        holder.bindData(banner)
        holder.itemView.setOnClickListener {
            mItemClickListener.itemClick(position)
        }
        if (position == mList.size-1) {
            holder.binding.cvInnerStories.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                marginEnd = context.resources.getDimension(R.dimen.dp_16).toInt()
            }
        } else {
            holder.binding.cvInnerStories.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                marginStart = context.resources.getDimension(R.dimen.dp_16).toInt()
            }
        }
    }

    class CommonAdapterViewHolder(var binding: HomeScreenStoriesItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(list: HomeScreenStoriesData) =
            binding.apply {
                mData = list
                executePendingBindings()
            }
    }

    interface ItemClickListener {
        fun itemClick(position: Int)
    }
}
