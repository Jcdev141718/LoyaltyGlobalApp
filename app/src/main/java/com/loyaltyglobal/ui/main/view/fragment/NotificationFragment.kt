package com.loyaltyglobal.ui.main.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.loyaltyglobal.data.model.NotificationData
import com.loyaltyglobal.databinding.FragmentNotificationBinding
import com.loyaltyglobal.ui.base.BaseFragment
import com.loyaltyglobal.ui.main.adapter.NotificationAdapter
import com.loyaltyglobal.ui.main.viewmodel.NotificationViewModel
import com.loyaltyglobal.util.hide
import com.loyaltyglobal.util.show

/**
 * Created by Abhin.
 */

class NotificationFragment : BaseFragment() {

    lateinit var mBinding: FragmentNotificationBinding
    lateinit var mAdapter: NotificationAdapter
    private var mNotificationList: ArrayList<NotificationData> = ArrayList()
    private val mNotificationViewModel: NotificationViewModel by viewModels()

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
        mAdapter = NotificationAdapter(mNotificationList)
        mBinding.rvNotification.layoutManager = LinearLayoutManager(requireContext())
        mBinding.rvNotification.adapter = mAdapter
    }

    private fun initObserver() {

        mNotificationViewModel.mutableNotificationList.observe(viewLifecycleOwner, {
            if (!it.isNullOrEmpty()) {
                mBinding.rvNotification.show()
                mNotificationList.addAll(it)
                mAdapter.notifyItemInserted(mNotificationList.size)
                mBinding.groupNoNotification.hide()
            } else {
                mBinding.groupNoNotification.show()
                mBinding.rvNotification.hide()
            }
        })
    }

    override fun onDestroyView() {
        showBottomNavigation()
        super.onDestroyView()
    }
}
