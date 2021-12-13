package com.loyaltyglobal.ui.main.view.fragment

/**
 * Created by Abhin.
 */
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.loyaltyglobal.R
import com.loyaltyglobal.databinding.FragmentHomeScreenBinding
import com.loyaltyglobal.ui.base.BaseFragment
import com.loyaltyglobal.ui.main.view.fragments.QrCodeScannerFragment
import com.loyaltyglobal.util.addReplaceFragment
import com.loyaltyglobal.util.clickWithDebounce
import com.loyaltyglobal.util.openBottomSheet
import com.loyaltyglobal.util.addReplaceFragment
import com.loyaltyglobal.util.setImage

class HomeScreenFragment : BaseFragment() {

    private lateinit var binding: FragmentHomeScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentHomeScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        clickListener()
    }

    private fun clickListener() {
        binding.layoutHomeScreenToolbar.imgNotification.setOnClickListener {
            hideBottomNavigation()
            val mNotificationFragment = NotificationFragment()
            activity?.addReplaceFragment(
                R.id.fl_main_container, mNotificationFragment,
                addFragment = true,
                addToBackStack = true
            )
        }
    }

    private fun init() {
        binding.layoutHomeScreenToolbar.imgLogo.setImage(R.drawable.icon_logo)
        binding.layoutHomeScreenToolbar.imgCamera.clickWithDebounce {
            activity?.addReplaceFragment(R.id.fl_container,
                QrCodeScannerFragment(), addFragment = true, addToBackStack = true)
        }
        binding.layoutHomeScreenToolbar.imgQrCode.clickWithDebounce { openBottomSheet(ShowQrBottomSheetFragment()) }
    }
}
