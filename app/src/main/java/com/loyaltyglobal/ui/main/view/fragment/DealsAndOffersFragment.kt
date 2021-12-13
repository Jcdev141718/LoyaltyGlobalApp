package com.loyaltyglobal.ui.main.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.loyaltyglobal.data.model.DealsAndOffersData
import com.loyaltyglobal.databinding.FragmentDealsAndOffersBinding
import com.loyaltyglobal.ui.base.BaseFragment
import com.loyaltyglobal.ui.main.adapter.DealsAndOffersAdapter
import com.loyaltyglobal.ui.main.viewmodel.DealsAndOffersViewModel
import com.loyaltyglobal.ui.main.viewmodel.ExploreViewModel
import com.loyaltyglobal.util.hide
import com.loyaltyglobal.util.show

class DealsAndOffersFragment : BaseFragment() {

    private lateinit var mBinding: FragmentDealsAndOffersBinding
    private lateinit var mAdapter: DealsAndOffersAdapter
    private var mDealsAndOfferList: ArrayList<DealsAndOffersData> = ArrayList()
    private val mExploreViewModel: ExploreViewModel by activityViewModels()

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
        mExploreViewModel.getDealsAndOffersList()
    }

    private fun setDealsAndOfferAdapter() {
        mAdapter = DealsAndOffersAdapter(mDealsAndOfferList)
        mBinding.rvExploreDealsOffers.layoutManager = LinearLayoutManager(requireContext())
        mBinding.rvExploreDealsOffers.adapter = mAdapter
    }

    private fun initObserver() {

        mExploreViewModel.mutableDealsAndOffersList.observe(viewLifecycleOwner, {
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
