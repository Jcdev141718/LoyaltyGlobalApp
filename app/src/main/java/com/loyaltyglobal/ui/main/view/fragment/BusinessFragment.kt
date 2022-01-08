package com.loyaltyglobal.ui.main.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.maps.model.LatLng
import com.loyaltyglobal.R
import com.loyaltyglobal.data.source.localModels.SubBrandAndCoalition
import com.loyaltyglobal.databinding.FragmentBusinessBinding
import com.loyaltyglobal.ui.base.BaseFragment
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentBusinessBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
    }

    private fun initObserver() {

        exploreViewModel.mutableBusinessList.observe(viewLifecycleOwner, {
            if (!it.isNullOrEmpty()) {
                binding.rvExploreBusiness.show()
                brandList.addAll(it)
                subBrandAdapter.submitList(brandList)
                binding.txtNoItemFound.hide()
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
}
