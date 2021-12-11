package com.loyaltyglobal.ui.main.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.loyaltyglobal.data.model.DealsAndOffersData
import com.loyaltyglobal.databinding.FragmentDealsAndOffersBinding
import com.loyaltyglobal.ui.base.BaseFragment
import com.loyaltyglobal.ui.main.view.adapters.DealsAndOffersAdapter
import com.loyaltyglobal.ui.main.view.viewmodels.DealsAndOffersViewModel
import com.loyaltyglobal.util.hide
import com.loyaltyglobal.util.show

class DealsAndOffersFragment : BaseFragment() {

    private lateinit var mBinding: FragmentDealsAndOffersBinding
    private lateinit var mAdapter: DealsAndOffersAdapter
    private var mDealsAndOfferList: ArrayList<DealsAndOffersData> = ArrayList()
    private val mDealsAndOffersViewModel: DealsAndOffersViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentDealsAndOffersBinding.inflate(layoutInflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObserver()
        setDealsAndOfferAdapter()
        mDealsAndOffersViewModel.getDealsAndOffersList()
    }

    private fun setDealsAndOfferAdapter() {
        mAdapter = DealsAndOffersAdapter(mDealsAndOfferList)
        mBinding.rvExploreDealsOffers.layoutManager = LinearLayoutManager(requireContext())
        mBinding.rvExploreDealsOffers.adapter = mAdapter
    }

    private fun initObserver() {

        mDealsAndOffersViewModel.mutableDealsAndOffersList.observe(viewLifecycleOwner, {
            if (!it.isNullOrEmpty()) {
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
