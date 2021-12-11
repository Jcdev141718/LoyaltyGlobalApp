package com.loyaltyglobal.ui.main.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.loyaltyglobal.R
import com.loyaltyglobal.data.model.DealsAndOffersData
import com.loyaltyglobal.databinding.ItemDealsOffersBinding

/**
 * Created by Abhin.
 */
class DealsAndOffersAdapter(var dealsList: ArrayList<DealsAndOffersData>) :
    RecyclerView.Adapter<DealsAndOffersAdapter.DealAndOffersViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DealAndOffersViewHolder {
        return DealAndOffersViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_deals_offers,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: DealAndOffersViewHolder, position: Int) {
        holder.bind(dealsList[position])
    }

    override fun getItemCount() = dealsList.size

    class DealAndOffersViewHolder(var itemCardListBinding: ItemDealsOffersBinding) :
        RecyclerView.ViewHolder(itemCardListBinding.root) {
        fun bind(dealsAndOffersData: DealsAndOffersData) = itemCardListBinding.apply {
            data = dealsAndOffersData
            executePendingBindings()
        }
    }
}
