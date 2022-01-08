package com.loyaltyglobal.ui.main.adapter

import android.content.Context
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import com.bumptech.glide.Glide
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import com.loyaltyglobal.R
import com.loyaltyglobal.data.source.localModels.SubBrandAndCoalition

/**
 * Created by Abhin.
 */
class MyInfoWindowAdapter(
    private var context: Context,
    private var mMyContentsView: View,
    private var mBusinessList: ArrayList<SubBrandAndCoalition>
) :
    GoogleMap.InfoWindowAdapter {

    override fun getInfoContents(p0: Marker): View? {
        val sSubBrandAndCoalition: SubBrandAndCoalition =
            mBusinessList.firstOrNull { p0.tag.toString() == it.subBrand._id }!!
        return if (p0.tag.toString() == sSubBrandAndCoalition.subBrand._id) {
            mMyContentsView.findViewById<AppCompatImageView>(R.id.img_brand_logo)?.let {
                Glide.with(context).load(sSubBrandAndCoalition.subBrand.brandLogo)
                    .placeholder(R.drawable.ic_logo_header_small).into(it)
            }
            mMyContentsView.findViewById<AppCompatTextView>(R.id.txt_brand_name)?.let {
                it.text = sSubBrandAndCoalition.subBrand.getBrandNameFirstCap()
            }
            mMyContentsView.findViewById<AppCompatTextView>(R.id.txt_brand_sub_title)?.let {
                it.text = sSubBrandAndCoalition.subBrand.location?.address?.trim()
            }

            mMyContentsView
        } else null
    }

    override fun getInfoWindow(p0: Marker): View? {
        val sSubBrandAndCoalition: SubBrandAndCoalition =
            mBusinessList.firstOrNull { p0.tag.toString() == it.subBrand._id }!!
        return if (p0.tag.toString() == sSubBrandAndCoalition.subBrand._id) {
            mMyContentsView.findViewById<AppCompatImageView>(R.id.img_brand_logo)?.let {
                Glide.with(context).load(sSubBrandAndCoalition.subBrand.brandLogo)
                    .placeholder(R.drawable.ic_logo_header_small).into(it)
            }
            mMyContentsView.findViewById<AppCompatTextView>(R.id.txt_brand_name)?.let {
                it.text = sSubBrandAndCoalition.subBrand.getBrandNameFirstCap()
            }
            mMyContentsView.findViewById<AppCompatTextView>(R.id.txt_brand_sub_title)?.let {
                it.text = sSubBrandAndCoalition.subBrand.location?.address?.trim()
            }

            mMyContentsView
        } else null
    }
}