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
import com.loyaltyglobal.data.model.AllDaysModel
import com.loyaltyglobal.data.source.localModels.SubBrandAndCoalition
import com.loyaltyglobal.databinding.FragmentExploreDetailsBinding
import com.loyaltyglobal.ui.main.adapter.AllDaysAdapter
import com.loyaltyglobal.ui.main.viewmodel.ExploreViewModel
import com.loyaltyglobal.util.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExploreDetailsFragment : Fragment() {

    private val exploreViewModel: ExploreViewModel by activityViewModels()
    private lateinit var binding: FragmentExploreDetailsBinding
    private var mMapFragment: SupportMapFragment? = null
    private lateinit var googleMap: GoogleMap
    private var allDayAdapter: AllDaysAdapter? = null
    private var allDaysList: ArrayList<AllDaysModel> = ArrayList()
    private var isAllDayExpand = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initObserver()
    }

    private fun initObserver() {
        exploreViewModel.mutableUrlLinks.observe(this, {
            if (!it.isNullOrEmpty()) {
                binding.txtSeeMore.show()
            } else {
                binding.txtSeeMore.hide()
            }
        })

        exploreViewModel.brandDetailsData.observe(this, {
            if (it != null) {
                setData(it)
            }
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentExploreDetailsBinding.inflate(inflater, container, false).apply {
            viewModel = exploreViewModel
            lifecycleOwner = this@ExploreDetailsFragment
            executePendingBindings()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        initClickListener()
        exploreViewModel.getBrandDetails() //        setData()
    }

    private fun initClickListener() {
        binding.apply {
            imgBack.clickWithDebounce {
                activity?.supportFragmentManager?.popBackStack()
            }
            txtSeeMore.clickWithDebounce {
                activity?.addReplaceFragment(R.id.fl_main_container, ExploreAllDetailsFragment(), true, true)
            }
            llSeeMore.clickWithDebounce {
                txtDiscountSeeMore.text = if (!isAllDayExpand) {
                    allDayAdapter?.submitList(allDaysList.take(allDaysList.size))
                    imgDiscountSeeMore.rotation = -90f
                    getString(R.string.see_less)
                } else {
                    allDayAdapter?.submitList(allDaysList.take(1))
                    imgDiscountSeeMore.rotation = 90f
                    getString(R.string.see_more)
                }
                isAllDayExpand = !isAllDayExpand
            }
        }
    }

    private fun setData(subBrandAndCoalition: SubBrandAndCoalition?) {
        exploreViewModel.getLinksData()
        subBrandAndCoalition.let { data ->
            binding.txtFoodDetails.setResizableText(data?.subBrand?.description.toString(), 2, false)
            initMap(LatLng(data?.subBrand?.location?.lat ?: 0.0, data?.subBrand?.location?.lng ?: 0.0), "")
        }
    }

    private fun initRecyclerView() {
        allDaysList = exploreViewModel.getDayList()
        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvDiscount.layoutManager = layoutManager
        allDayAdapter = AllDaysAdapter()
        allDayAdapter?.submitList(allDaysList.take(1))
        binding.rvDiscount.adapter = allDayAdapter
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
