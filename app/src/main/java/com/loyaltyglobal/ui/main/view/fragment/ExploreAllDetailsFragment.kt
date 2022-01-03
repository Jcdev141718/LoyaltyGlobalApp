package com.loyaltyglobal.ui.main.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.loyaltyglobal.R
import com.loyaltyglobal.databinding.FragmentExploreAllDetailsBinding
import com.loyaltyglobal.ui.main.adapter.LinksAdapter
import com.loyaltyglobal.ui.main.viewmodel.ExploreViewModel
import com.loyaltyglobal.util.clickWithDebounce
import com.loyaltyglobal.util.setResizableText
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by Abhin.
 */

@AndroidEntryPoint
class ExploreAllDetailsFragment : Fragment() {

    private val exploreViewModel: ExploreViewModel by activityViewModels()
    private lateinit var binding : FragmentExploreAllDetailsBinding
    private var mMapFragment: SupportMapFragment? = null
    private lateinit var googleMap: GoogleMap

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentExploreAllDetailsBinding.inflate(inflater,container,false).apply {
            viewModel = exploreViewModel
            lifecycleOwner = this@ExploreAllDetailsFragment
            executePendingBindings()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        setData()
    }

    private fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvLinks.layoutManager = layoutManager
        val linkAdapter = LinksAdapter(arrayListOf(), object : LinksAdapter.LinkClickListeners {
            override fun onClick(position: Int) {
                //TODO : Open webView
                /*activity?.addReplaceFragment(R.id.fl_main_container, WebViewFragment().apply {
                    arguments = Bundle().apply {
                        putString(Constants.KEY_WEB_URL, mHomeScreenDealPromotionsList[position].value)
                    }
                }, true, true)
                (activity as MainActivity).showHideBottomNavigationBar(false)*/
            }
        })
        binding.rvLinks.adapter = linkAdapter
    }

    private fun setData() {
        binding.imgBack.clickWithDebounce { activity?.supportFragmentManager?.popBackStack() }
        exploreViewModel.brandDetailsData.value.let { data ->
            binding.txtFoodDetails.setResizableText(data?.subBrand?.description.toString(), 2, false)
            initMap(LatLng(data?.subBrand?.location?.lat ?: 0.0, data?.subBrand?.location?.lng ?: 0.0), "")
        }
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
