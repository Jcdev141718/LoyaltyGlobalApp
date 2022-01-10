package com.loyaltyglobal.ui.main.view.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.loyaltyglobal.R
import com.loyaltyglobal.data.source.localModels.SubBrandAndCoalition
import com.loyaltyglobal.databinding.FragmentBusinessBinding
import com.loyaltyglobal.ui.base.BaseFragment
import com.loyaltyglobal.ui.main.adapter.MyInfoWindowAdapter
import com.loyaltyglobal.ui.main.adapter.SubBrandAdapter
import com.loyaltyglobal.ui.main.view.activity.MainActivity
import com.loyaltyglobal.ui.main.viewmodel.ExploreViewModel
import com.loyaltyglobal.util.addReplaceFragment
import com.loyaltyglobal.util.clickWithDebounce
import com.loyaltyglobal.util.hide
import com.loyaltyglobal.util.show


/**
 * Created by Abhin.
 */

class BusinessFragment : BaseFragment() {

    private lateinit var subBrandAdapter: SubBrandAdapter
    private lateinit var binding: FragmentBusinessBinding
    private var brandList: ArrayList<SubBrandAndCoalition> = ArrayList()
    private val exploreViewModel: ExploreViewModel by activityViewModels()
    private lateinit var googleMap: GoogleMap
    private var mMapFragment: SupportMapFragment? = null
    private var mMyContentsView: View? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentBusinessBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mMyContentsView = layoutInflater.inflate(R.layout.item_map_window, null)
        initObserver()
        setBusinessAdapter()
        exploreViewModel.getBusinessList()
    }

    private fun setBusinessAdapter() {
        binding.txtResetFilter.clickWithDebounce {
            binding.txtResetFilter.hide()
            exploreViewModel.mutableFilterList.value?.clear()
            exploreViewModel.mutableFiltersList.value?.clear()
            subBrandAdapter.submitList(brandList)
        }
        val latitude = (activity as MainActivity).gpsTracker?.getLatitude()
        val longitude = (activity as MainActivity).gpsTracker?.getLongitude()
        val latLong = latitude?.let { lat ->
            longitude?.let { long ->
                LatLng(lat, long)
            }
        }
        subBrandAdapter = SubBrandAdapter(latLong,object : SubBrandAdapter.SubBrandItemClickListener {
            override fun clickListener(position: Int) {
                exploreViewModel.brandDetailsData.value = brandList[position]
                activity?.addReplaceFragment(R.id.fl_main_container, ExploreDetailsFragment(), true, true)
            }
        })
        binding.rvExploreBusiness.layoutManager = LinearLayoutManager(requireContext())
        binding.rvExploreBusiness.adapter = subBrandAdapter
//        mAdapter = SubBrandAdapter(
//            latLong,
//            mBusinessList,
//            object : SubBrandAdapter.SubBrandItemClickListener {
//                override fun clickListener(position: Int) {
//                    navigateToExploreDetailFragment(mBusinessList[position])
//                }
//            })
//        mBinding.rvExploreBusiness.layoutManager = LinearLayoutManager(requireContext())
//        mBinding.rvExploreBusiness.adapter = mAdapter
    }

    private fun navigateToExploreDetailFragment(subBrandAndCoalition: SubBrandAndCoalition) {
        exploreViewModel.brandDetailsData.value = subBrandAndCoalition
        activity?.addReplaceFragment(
            R.id.fl_main_container,
            ExploreDetailsFragment(),
            addFragment = true,
            addToBackStack = true
        )
    }

    private fun initObserver() {

        exploreViewModel.mutableBusinessList.observe(viewLifecycleOwner, {
            if (!it.isNullOrEmpty()) {
                binding.rvExploreBusiness.show()
                brandList.addAll(it)
                subBrandAdapter.submitList(brandList)
                binding.txtNoItemFound.hide()
                initMap()
            } else {
                binding.txtNoItemFound.show()
                binding.rvExploreBusiness.hide()
            }
        })

        exploreViewModel.mutableFiltersList.observe(viewLifecycleOwner, { filtersList ->
            if (!filtersList.isNullOrEmpty()) {
                subBrandAdapter.submitList(brandList.filter { filtersList.contains(it.subBrand.locationType) })
                binding.txtResetFilter.show()
            } else {
                binding.txtResetFilter.hide()
            }
        })
    }

    @SuppressLint("PotentialBehaviorOverride")
    private fun initMap() {
        mMapFragment =
            childFragmentManager.findFragmentById(R.id.map_fragment_business) as? SupportMapFragment
        mMapFragment?.getMapAsync { it ->
            googleMap = it
            for (i in 0 until brandList.size) {
                val latLng = brandList[i].subBrand.location?.lng?.let { it1 ->
                    brandList[i].subBrand.location?.lat?.let { it2 ->
                        LatLng(
                            it2,
                            it1
                        )
                    }
                }
                latLng?.let { latlng ->
                    val marker: Marker = googleMap.addMarker(
                        MarkerOptions().position(latlng)
                    )!!
                    marker.tag = brandList[i].subBrand._id
                    googleMap.animateCamera(CameraUpdateFactory.zoomTo(18.0f))
                    googleMap.moveCamera(CameraUpdateFactory.newLatLng(latlng))
                    marker.showInfoWindow()
                }
            }
            googleMap.setInfoWindowAdapter(mMyContentsView?.let { it1 ->
                MyInfoWindowAdapter(
                    requireContext(),
                    it1, brandList
                )
            })

            googleMap.setOnInfoWindowClickListener { marker ->
                val sSubBrandAndCoalition: SubBrandAndCoalition =
                    brandList.firstOrNull { marker.tag.toString() == it.subBrand._id }!!
                navigateToExploreDetailFragment(sSubBrandAndCoalition)
            }
        }
    }

    /* @SuppressLint("InflateParams")
     private fun getMarkerBitmapFromView(resId: String): Bitmap? {
         val customMarkerView: View =
             layoutInflater.inflate(R.layout.map_custom_marker, null)
         val imgBrandBg: AppCompatImageView =
             customMarkerView.findViewById(R.id.img_bg)
         val imgBrand: ShapeableImageView =
             customMarkerView.findViewById(R.id.img_brand_logo)

         imgBrandBg.setImageResource(R.drawable.ic_custom_marker)
         Glide.with(this).load(resId).into(imgBrand)

         var brandBGBitmap: Bitmap? = null
         imgBrandBg.post {
             brandBGBitmap =
                 Bitmap.createBitmap(imgBrandBg.width, imgBrandBg.height, Bitmap.Config.RGB_565)
         }

         var brandBitmap: Bitmap? = null
         imgBrand.post {
             brandBitmap =
                 Bitmap.createBitmap(imgBrand.width, imgBrand.height, Bitmap.Config.RGB_565)
         }

         val bmOverlay =
             Bitmap.createBitmap(
                 brandBGBitmap?.width!!,
                 brandBGBitmap?.height!!,
                 brandBGBitmap!!.config
             )
         val canvas = Canvas(bmOverlay)

         canvas.drawBitmap(brandBGBitmap!!, Matrix(), null)
         canvas.drawBitmap(brandBitmap!!, 0f, 0f, null)

         return bmOverlay
     }*/

    fun switchMap(isSwitch: Boolean) {
        if (isSwitch) {
            binding.rvExploreBusiness.hide()
            binding.mapFragmentBusiness.show()
        } else {
            binding.rvExploreBusiness.show()
            binding.mapFragmentBusiness.hide()
        }
    }
}
