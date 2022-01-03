package com.loyaltyglobal.ui.main.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.loyaltyglobal.data.model.DealsAndOffersData
import com.loyaltyglobal.data.source.localModels.subBrandResponse.DealOffer
import com.loyaltyglobal.data.source.localModels.subBrandResponse.SubBrand
import com.loyaltyglobal.databinding.FragmentDealsAndOffersBinding
import com.loyaltyglobal.ui.base.BaseFragment
import com.loyaltyglobal.ui.main.adapter.DealsAndOffersAdapter
import com.loyaltyglobal.ui.main.viewmodel.ExploreViewModel
import com.loyaltyglobal.util.hide
import com.loyaltyglobal.util.show

/**
 * Created by Abhin.
 */

class DealsAndOffersFragment : BaseFragment() {

    private lateinit var mBinding: FragmentDealsAndOffersBinding
    private lateinit var mAdapter: DealsAndOffersAdapter
    private var mDealsAndOfferList: ArrayList<DealOffer> = ArrayList()
    private val mExploreViewModel: ExploreViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initObserver()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentDealsAndOffersBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setDealsAndOfferAdapter()
        mExploreViewModel.getDealAndOffersList()
    }

    private fun setDealsAndOfferAdapter() {
        mAdapter = DealsAndOffersAdapter(mDealsAndOfferList)
        mBinding.rvExploreDealsOffers.layoutManager = LinearLayoutManager(requireContext())
        mBinding.rvExploreDealsOffers.adapter = mAdapter
    }

    private fun initObserver() {
        mExploreViewModel.mutableDealsAndOffersList.observe(this, {
            if (!it.isNullOrEmpty()) {
                Log.e("TAG","initObserver --> ${Gson().toJson(it)}")
                mBinding.rvExploreDealsOffers.show()
                mDealsAndOfferList.addAll(it)
                mAdapter.notifyItemInserted(mDealsAndOfferList.size)
                mBinding.llNoDealsOfferFound.hide()
            } else {
                mBinding.llNoDealsOfferFound.show()
                mBinding.rvExploreDealsOffers.hide()
            }
        })
    }
}
