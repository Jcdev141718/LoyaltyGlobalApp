package com.loyaltyglobal.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.loyaltyglobal.R
import com.loyaltyglobal.data.model.response.HomeScreenDealPromotionsData
import com.loyaltyglobal.data.source.localModels.userPassResponse.CustomField
import com.loyaltyglobal.databinding.HomeScreenDealsPromotionsItemBinding

/**
 * Created by Abhin.
 */
class HomeScreenDealPromotionsAdapter(
    private var mList: List<CustomField>,
    private val mItemClickListener: ItemClickListener
) : RecyclerView.Adapter<HomeScreenDealPromotionsAdapter.CommonAdapterViewHolder>() {

    var binding: HomeScreenDealsPromotionsItemBinding? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommonAdapterViewHolder {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_home_screen_deals_promotions_offers,
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
    }

    class CommonAdapterViewHolder(var binding: HomeScreenDealsPromotionsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(list: CustomField) =
            binding.apply {
                mData = list
                executePendingBindings()
            }
    }

    interface ItemClickListener {
        fun itemClick(position: Int)
    }
}
