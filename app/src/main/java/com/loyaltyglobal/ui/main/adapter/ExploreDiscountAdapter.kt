package com.loyaltyglobal.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.loyaltyglobal.R
import com.loyaltyglobal.databinding.ItemExploreDiscountBinding
import com.loyaltyglobal.util.clickWithDebounce
import java.util.ArrayList

/**
 * Created by Abhin.
 */
class ExploreDiscountAdapter(private var discountList: ArrayList<String>, private var clickInterface : StoriesAdapter.ClickListener) : RecyclerView.Adapter<ExploreDiscountAdapter.DataViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder =
        DataViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_explore_discount,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(discountList[position])
        holder.itemView.clickWithDebounce {
            clickInterface.itemClick(position)
        }
    }

    class DataViewHolder(var binding: ItemExploreDiscountBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(itemData: String) = binding.apply {

            // mData = itemData
            // executePendingBindings()
        }
    }

    fun setData(list: List<String>, loadMore: Boolean) {
        val oldListSize = this.discountList.size
        if (!loadMore) {
            discountList.clear()
            discountList.addAll(list)
        } else {
            discountList.addAll(list)
        }
        notifyItemRangeChanged(oldListSize, list.size)
    }

    override fun getItemCount(): Int = discountList.size

}
