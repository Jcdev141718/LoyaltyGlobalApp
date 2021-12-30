package com.loyaltyglobal.ui.main.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.loyaltyglobal.R
import com.loyaltyglobal.databinding.FragmentExploreDetailsBinding

import com.loyaltyglobal.util.setResizableText


class ExploreDetailsFragment : Fragment() {

    lateinit var binding: FragmentExploreDetailsBinding
    private var mMapFragment: SupportMapFragment? = null
    private lateinit var googleMap: GoogleMap


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentExploreDetailsBinding.inflate(layoutInflater, container, false)
        setData()
        return binding.root
    }

    private fun setData() {

        binding.apply {
           txtFoodDetails.text = getString(R.string.american_style_cluccin_good)
            txtFoodDetails.setResizableText(getString(R.string.american_style_cluccin_good),2,false)

            imgBack.setOnClickListener {

            }

            imgSeeMoreNext.setOnClickListener {

            }
        }



        initMap(LatLng(23.0301,72.5178),"ahmedabad")
    }

    private fun initMap(latLong: LatLng?, markerName: String?) {
        mMapFragment = childFragmentManager.findFragmentById(R.id.map_fragment) as? SupportMapFragment
        mMapFragment?.getMapAsync {
            googleMap = it
            latLong?.let { latLong ->
                markerName?.let { markerName ->
                    googleMap.addMarker(MarkerOptions().position(latLong).title(markerName))
                }
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLong, 16.0f))
            }
        }
    }


}
