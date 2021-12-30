package com.loyaltyglobal.ui.main.view.fragment

import android.os.Bundle
import android.text.TextUtils.concat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.loyaltyglobal.R
import com.loyaltyglobal.data.model.AllDaysModel
import com.loyaltyglobal.data.source.localModels.subBrandResponse.SubBrand
import com.loyaltyglobal.databinding.FragmentExploreDetailsBinding
import com.loyaltyglobal.ui.main.adapter.AllDaysAdapter
import com.loyaltyglobal.ui.main.viewmodel.ExploreViewModel
import com.loyaltyglobal.util.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExploreDetailsFragment : Fragment() {

    private val exploreViewModel: ExploreViewModel by viewModels()
    private lateinit var binding: FragmentExploreDetailsBinding
    private var mMapFragment: SupportMapFragment? = null
    private lateinit var googleMap: GoogleMap
    private var brandDetailsData: SubBrand? = null
    private var allDayAdapter: AllDaysAdapter? = null
    private var allDaysList: ArrayList<AllDaysModel> = ArrayList()
    private var isAllDayExpand = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getParcelable<SubBrand>(Constants.KEY_SUB_BRAND_DATA)?.let {
            brandDetailsData = it
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentExploreDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setData()
    }

    private fun setData() {
        brandDetailsData?.let { data ->
            binding.apply {
                imgBrandLogo.setImage(data.brandLogo.toString(), true)
                txtBrandName.text = data.brandName?.firstLetterCap()
                txtSubTitle.text = data.locationType?.firstLetterCap()
                txtAddress.text = data.location?.address
                txtFoodDetails.text = data.description
                txtFoodDetails.setResizableText(data.description.toString(), 2, false)
                txtPhoneNumber.text = concat(data.dialingCode, data.phone)
                txtEmailAddress.text = data.email
                txtPoint.text =
                    data.earnPointPer?.let { resources.getQuantityString(R.plurals.some_point, it, data.earnPointPer) }
                imgBack.clickWithDebounce {
                    activity?.supportFragmentManager?.popBackStack()
                }
                initRecyclerView()
                imgSeeMoreNext.clickWithDebounce {}
                txtDiscountSeeMore.clickWithDebounce {
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
            initMap(LatLng(data.location?.lat ?: 0.0, data.location?.lng ?: 0.0), "")

        }
    }

    private fun initRecyclerView() {
        allDaysList = exploreViewModel.getDayList("30")
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
