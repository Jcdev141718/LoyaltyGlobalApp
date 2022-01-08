package com.loyaltyglobal.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.model.LatLng
import com.loyaltyglobal.R
import com.loyaltyglobal.data.source.localModels.SubBrandAndCoalition
import com.loyaltyglobal.databinding.ItemBusinessBinding
import com.loyaltyglobal.util.getDistance

/**
 * Created by Abhin.
 */
class SubBrandAdapter(
    private var userLocation: LatLng?,
    private var listener: SubBrandItemClickListener,
) : ListAdapter<SubBrandAndCoalition, SubBrandAdapter.BusinessesViewHolder>(DiffCallback) {

    interface SubBrandItemClickListener {
        fun clickListener(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BusinessesViewHolder {
        return BusinessesViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context),
            R.layout.item_business,
            parent,
            false))
    }

    override fun onBindViewHolder(holder: BusinessesViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener { listener.clickListener(position) }
        val brandLocation = getItem(position).subBrand.location?.let { location ->
            LatLng(location.lat!!, location.lat!!)
        }
        userLocation?.let { latLng ->
            holder.itemCardListBinding.txtDistance.text = brandLocation?.let { getDistance(latLng, it) }
        }
    }

    class BusinessesViewHolder(var itemCardListBinding: ItemBusinessBinding) : RecyclerView.ViewHolder(itemCardListBinding.root) {
        fun bind(card: SubBrandAndCoalition) = itemCardListBinding.apply {
            data = card
            executePendingBindings()
        }
    }

    object DiffCallback : DiffUtil.ItemCallback<SubBrandAndCoalition>() {
        override fun areItemsTheSame(oldItem: SubBrandAndCoalition, newItem: SubBrandAndCoalition): Boolean {
            return oldItem.subBrand._id == oldItem.subBrand._id
        }

        override fun areContentsTheSame(oldItem: SubBrandAndCoalition, newItem: SubBrandAndCoalition): Boolean {
            return oldItem == newItem
        }
    }
}
