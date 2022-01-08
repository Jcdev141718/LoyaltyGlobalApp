package com.loyaltyglobal.ui.main.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.loyaltyglobal.R
import com.loyaltyglobal.data.source.localModels.NotificationAndSubBrand
import com.loyaltyglobal.data.source.localModels.SubBrandAndCoalition
import com.loyaltyglobal.databinding.FragmentNotificationBinding
import com.loyaltyglobal.ui.base.BaseFragment
import com.loyaltyglobal.ui.main.adapter.NotificationAdapter
import com.loyaltyglobal.ui.main.viewmodel.ExploreViewModel
import com.loyaltyglobal.ui.main.viewmodel.NotificationViewModel
import com.loyaltyglobal.util.Constants.BRAND_AND_NOTIFICATION
import com.loyaltyglobal.util.addReplaceFragment
import com.loyaltyglobal.util.hide
import com.loyaltyglobal.util.show
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by Abhin.
 */
@AndroidEntryPoint
class NotificationFragment : BaseFragment() {

    private lateinit var mAdapter: NotificationAdapter
    lateinit var mBinding: FragmentNotificationBinding

    private val mNotificationViewModel: NotificationViewModel by viewModels()
    private val exploreViewModel: ExploreViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentNotificationBinding.inflate(layoutInflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mNotificationViewModel.getNotificationList()
        clickListener()
        initObserver()
        setBusinessAdapter()
    }

    private fun clickListener() {
        mBinding.imgNotificationBack.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }
    }

    private fun setBusinessAdapter() {
        mBinding.apply {
            rvNotification.layoutManager = LinearLayoutManager(requireContext())
            rvNotification.adapter = mAdapter
        }

    }

    private fun initObserver() {

        mAdapter = NotificationAdapter(object : NotificationAdapter.OnNotificationClick{
             override fun onItemClick(itemData: NotificationAndSubBrand) {
                 if(itemData.notification.type == BRAND_AND_NOTIFICATION){
                     exploreViewModel.brandId = itemData.subBrand._id
                     activity?.addReplaceFragment(R.id.fl_main_container,ExploreDetailsFragment(),addFragment = true, addToBackStack = true)
                 }
             }

        })

        mNotificationViewModel.mutableNotificationList.observe(viewLifecycleOwner, {
            mBinding.apply {
                if (!it.isNullOrEmpty()) {
                    rvNotification.show()
                    mAdapter.submitList(it)
                    groupNoNotification.hide()
                } else {
                    groupNoNotification.show()
                    rvNotification.hide()
                }
            }

        })
    }

    override fun onDestroyView() {
        showBottomNavigation()
        super.onDestroyView()
    }
}
