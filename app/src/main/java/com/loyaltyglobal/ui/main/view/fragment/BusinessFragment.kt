package com.loyaltyglobal.ui.main.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.loyaltyglobal.R
import com.loyaltyglobal.data.source.localModels.subBrandResponse.SubBrand
import com.loyaltyglobal.databinding.FragmentBusinessBinding
import com.loyaltyglobal.ui.base.BaseFragment
import com.loyaltyglobal.ui.main.adapter.SubBrandAdapter
import com.loyaltyglobal.ui.main.viewmodel.ExploreViewModel
import com.loyaltyglobal.util.Constants
import com.loyaltyglobal.util.addReplaceFragment
import com.loyaltyglobal.util.hide
import com.loyaltyglobal.util.show

/**
 * Created by Abhin.
 */

class BusinessFragment : BaseFragment() {

    private lateinit var mAdapter: SubBrandAdapter
    private lateinit var mBinding: FragmentBusinessBinding
    private var mBusinessList: ArrayList<SubBrand> = ArrayList()
    private val mBusinessViewModel: ExploreViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        mBinding = FragmentBusinessBinding.inflate(layoutInflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObserver()
        setBusinessAdapter()
        mBusinessViewModel.getBusinessList()
    }

    private fun setBusinessAdapter() {
        mAdapter = SubBrandAdapter(mBusinessList, object : SubBrandAdapter.SubBrandItemClickListener {
            override fun clickListener(position: Int) {
                activity?.addReplaceFragment(R.id.fl_main_container, ExploreDetailsFragment().apply {
                    arguments = Bundle().apply {
                        putParcelable(Constants.KEY_SUB_BRAND_DATA, mBusinessList[position])
                    }
                }, true, true)
            }

        })
        mBinding.rvExploreBusiness.layoutManager = LinearLayoutManager(requireContext())
        mBinding.rvExploreBusiness.adapter = mAdapter
    }

    private fun initObserver() {

        mBusinessViewModel.mutableBusinessList.observe(viewLifecycleOwner, {
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
