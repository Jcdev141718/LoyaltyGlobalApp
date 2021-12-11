package com.loyaltyglobal.ui.main.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.loyaltyglobal.data.model.BusinessData
import com.loyaltyglobal.databinding.FragmentBusinessBinding
import com.loyaltyglobal.ui.base.BaseFragment
import com.loyaltyglobal.ui.main.view.adapters.BusinessAdapter
import com.loyaltyglobal.ui.main.view.viewmodels.BusinessViewModel
import com.loyaltyglobal.util.hide
import com.loyaltyglobal.util.show

class BusinessFragment : BaseFragment() {

    lateinit var mAdapter: BusinessAdapter
    lateinit var mBinding: FragmentBusinessBinding
    private var mBusinessList: ArrayList<BusinessData> = ArrayList()
    private val mBusinessViewModel: BusinessViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
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
        mAdapter = BusinessAdapter(mBusinessList)
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
