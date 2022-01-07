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
import com.loyaltyglobal.util.hide
import com.loyaltyglobal.util.show

/**
 * Created by Abhin.
 */

class BusinessFragment : BaseFragment() {

    private lateinit var mAdapter: SubBrandAdapter
    private lateinit var mBinding: FragmentBusinessBinding
    private var mBusinessList: ArrayList<SubBrandAndCoalition> = ArrayList()
    private val exploreViewModel: ExploreViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        mBinding = FragmentBusinessBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObserver()
        setBusinessAdapter()
        exploreViewModel.getBusinessList()
    }

    private fun setBusinessAdapter() {
        val latitude = (activity as MainActivity).gpsTracker?.getLatitude()
        val longitude = (activity as MainActivity).gpsTracker?.getLongitude()
        val latLong = latitude?.let { lat ->
            longitude?.let { long ->
                LatLng(lat, long)
            }

        }
        mAdapter = SubBrandAdapter(latLong, mBusinessList, object : SubBrandAdapter.SubBrandItemClickListener {
            override fun clickListener(position: Int) {
                exploreViewModel.brandDetailsData.value = mBusinessList[position]
                activity?.addReplaceFragment(R.id.fl_main_container, ExploreDetailsFragment(), true, true)
            }

        })
        mBinding.rvExploreBusiness.layoutManager = LinearLayoutManager(requireContext())
        mBinding.rvExploreBusiness.adapter = mAdapter
    }

    private fun initObserver() {

        exploreViewModel.mutableBusinessList.observe(viewLifecycleOwner, {
            if (!it.isNullOrEmpty()) {
                mBinding.rvExploreBusiness.show()
                mBusinessList.addAll(it)
                mAdapter.notifyItemInserted(mBusinessList.size)
                mBinding.txtNoItemFound.hide()
            } else {
                mBinding.txtNoItemFound.show()
                mBinding.rvExploreBusiness.hide()
            }
        })
    }
}
