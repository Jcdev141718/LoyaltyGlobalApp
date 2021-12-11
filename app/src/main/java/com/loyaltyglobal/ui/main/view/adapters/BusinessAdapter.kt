package com.loyaltyglobal.ui.main.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.loyaltyglobal.R
import com.loyaltyglobal.data.model.BusinessData
import com.loyaltyglobal.databinding.ItemBusinessBinding

/**
 * Created by Abhin.
 */
class BusinessAdapter(var businessList: ArrayList<BusinessData>) :
    RecyclerView.Adapter<BusinessAdapter.BusinessesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BusinessesViewHolder {
        return BusinessesViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_business,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: BusinessesViewHolder, position: Int) {
        holder.bind(businessList[position])
    }

    override fun getItemCount() = businessList.size

    class BusinessesViewHolder(var itemCardListBinding: ItemBusinessBinding) :
        RecyclerView.ViewHolder(itemCardListBinding.root) {
        fun bind(card: BusinessData) = itemCardListBinding.apply {
            data = card
            executePendingBindings()
        }
    }
}
