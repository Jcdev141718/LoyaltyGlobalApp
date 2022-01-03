package com.loyaltyglobal.ui.main.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.loyaltyglobal.data.model.ExploreFilterData
import com.loyaltyglobal.databinding.FragmentExploreFilterBinding
import com.loyaltyglobal.ui.base.BaseFragment
import com.loyaltyglobal.ui.main.adapter.ExploreFilterAdapter
import com.loyaltyglobal.ui.main.viewmodel.ExploreViewModel

/**
 * Created by Abhin.
 */

class ExploreFilterFragment : BaseFragment() {

    private var mExploreFilterInterface: ExploreFilterInterface? = null
    private lateinit var mBinding: FragmentExploreFilterBinding
    lateinit var mAdapter: ExploreFilterAdapter
    private var mFilterList: ArrayList<String> = ArrayList()
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
            mExploreFilterInterface?.applyFilter()
            activity?.supportFragmentManager?.popBackStack()
        }
    }

    private fun setExploreFilterAdapter() {
        mAdapter = ExploreFilterAdapter(mFilterList)
        mBinding.rvExploreFilter.layoutManager = LinearLayoutManager(requireContext())
        mBinding.rvExploreFilter.adapter = mAdapter
    }

    private fun initObserver() {
        mExploreViewModel.mutableFilterList.observe(viewLifecycleOwner, {
            mFilterList.clear()
            if (!it.isNullOrEmpty()) {
                mFilterList.addAll(it)
                mAdapter.notifyItemInserted(mFilterList.size)
            }
        })
    }

    override fun onDestroyView() {
        showBottomNavigation()
        super.onDestroyView()
    }

    interface ExploreFilterInterface {
        fun applyFilter()
    }

    fun setFilterInterface(listener : ExploreFilterInterface) {
        this.mExploreFilterInterface = listener
    }
}
