package com.loyaltyglobal.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.SphericalUtil
import com.loyaltyglobal.R
import com.loyaltyglobal.data.source.localModels.SubBrandAndCoalition
import com.loyaltyglobal.databinding.ItemBusinessBinding

/**
 * Created by Abhin.
 */
class SubBrandAdapter(
    var userLocation: LatLng?,
    var businessList: ArrayList<SubBrandAndCoalition>,
    private var listener: SubBrandItemClickListener,
) : RecyclerView.Adapter<SubBrandAdapter.BusinessesViewHolder>() {

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
        holder.bind(businessList[position])
        holder.itemView.setOnClickListener { listener.clickListener(position) }
        val brandLocation = businessList[position].subBrand.location?.let { location ->
            LatLng(location.lat!!, location.lat!!)
        }
        userLocation?.let { latLng ->
            holder.itemCardListBinding.txtDistance.text = SphericalUtil.computeDistanceBetween(latLng, brandLocation).toString()
        }
    }

    override fun getItemCount() = businessList.size

    class BusinessesViewHolder(var itemCardListBinding: ItemBusinessBinding) : RecyclerView.ViewHolder(itemCardListBinding.root) {
        fun bind(card: SubBrandAndCoalition) = itemCardListBinding.apply {
            data = card
            executePendingBindings()
        }
    }
}
