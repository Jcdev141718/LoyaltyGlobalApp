package com.loyaltyglobal.ui.main.view.activity

import android.os.Bundle
import com.loyaltyglobal.R
import com.loyaltyglobal.data.source.localModels.userPassResponse.Notification
import com.loyaltyglobal.databinding.ActivityFullScreenNotificationBinding
import com.loyaltyglobal.ui.base.BaseActivity
import com.loyaltyglobal.ui.main.view.fragment.WebViewFragment
import com.loyaltyglobal.util.Constants
import com.loyaltyglobal.util.Constants.KEY_WEB_URL
import com.loyaltyglobal.util.addReplaceFragment
import com.loyaltyglobal.util.hide
import com.loyaltyglobal.util.show
import dagger.hilt.android.AndroidEntryPoint


/**
 * Create by Abhin.
 */

@AndroidEntryPoint
class FullscreenNotificationActivity : BaseActivity() {

    private lateinit var binding: ActivityFullScreenNotificationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFullScreenNotificationBinding.inflate(layoutInflater).apply {
            lifecycleOwner = this@FullscreenNotificationActivity
        }
        setContentView(binding.root)
        init()
    }

    override fun onDestroy() {
        binding.clNotificationView.show()
        binding.flNotificationContainer.hide()
        super.onDestroy()
    }

    private fun init() {
        intent.getParcelableExtra<Notification>(Constants.NOTIFICATION_KEY)?.let { notificationData ->
            binding.mData = notificationData
            binding.executePendingBindings()
            val fragment = WebViewFragment().apply {
                arguments = Bundle().apply {
                    putString(KEY_WEB_URL, notificationData.linkUrl)
                }
            }
            binding.clVisitLink.apply {
                show()
                setOnClickListener {
                    addReplaceFragment(R.id.fl_notification_container, fragment, addFragment = true, addToBackStack = true)
                    binding.clNotificationView.hide()
                    binding.flNotificationContainer.show()
                }
            }
            binding.imgCloseNotification.setOnClickListener {
                onBackPressed()
            }
        }
    }
}

