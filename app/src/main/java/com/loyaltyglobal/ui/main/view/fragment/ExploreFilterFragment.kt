package com.loyaltyglobal.ui.main.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.loyaltyglobal.R
import com.loyaltyglobal.data.source.localModels.FilterModel
import com.loyaltyglobal.databinding.FragmentExploreFilterBinding
import com.loyaltyglobal.ui.base.BaseFragment
import com.loyaltyglobal.ui.main.adapter.ExploreFilterAdapter
import com.loyaltyglobal.ui.main.viewmodel.ExploreViewModel

/**
 * Created by Abhin.
 */

class ExploreFilterFragment : BaseFragment() {

    private lateinit var mBinding: FragmentExploreFilterBinding
    lateinit var mAdapter: ExploreFilterAdapter
    private var mFilterList: ArrayList<FilterModel> = ArrayList()
    private val mExploreViewModel: ExploreViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        mBinding = FragmentExploreFilterBinding.inflate(inflater,container,false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mExploreViewModel.getFilterList()
        initObserver()
        setExploreFilterAdapter()
        clickListener()
    }

    private fun clickListener() {
        mBinding.imgNotificationBack.setOnClickListener { activity?.supportFragmentManager?.popBackStack() }
        mBinding.txtClear.setOnClickListener { }
        mBinding.txtApply.setOnClickListener {
            mExploreViewModel.setFilters(mFilterList.filter { it.isSelected }.map { it.filterTitle.toString() }, true)
            activity?.supportFragmentManager?.popBackStack()
        }
    }

    private fun setExploreFilterAdapter() {
        mAdapter = ExploreFilterAdapter(object : ExploreFilterAdapter.FilterCallback {
            override fun onFilter(position: Int, isChecked: Boolean) {
                mFilterList[position].isSelected = isChecked
                setButtonEnable(mFilterList.any { it.isSelected })
                mAdapter.notifyItemChanged(position)
            }
        })
        mBinding.rvExploreFilter.layoutManager = LinearLayoutManager(requireContext())
        mBinding.rvExploreFilter.adapter = mAdapter
    }

    private fun initObserver() {
        mExploreViewModel.mutableFilterList.observe(viewLifecycleOwner, {
            mFilterList.clear()
            if (!it.isNullOrEmpty()) {
                mFilterList.addAll(it)
                mAdapter.submitList(mFilterList)
            }
        })
    }

    override fun onDestroyView() {
        showBottomNavigation()
        super.onDestroyView()
    }

    private fun setButtonEnable(isEnable : Boolean) {
        if (isEnable){
            mBinding.txtApply.setBackgroundResource(R.drawable.explore_apply_btn_bg)
            mBinding.txtApply.isEnabled = true
        } else {
            mBinding.txtApply.setBackgroundResource(R.drawable.shape_filled_button_disable)
            mBinding.txtApply.isEnabled = false
        }
    }
}
