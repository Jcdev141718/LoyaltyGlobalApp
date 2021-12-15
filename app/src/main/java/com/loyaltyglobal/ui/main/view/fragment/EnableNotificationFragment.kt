package com.loyaltyglobal.ui.main.view.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.loyaltyglobal.data.source.network.ApiResponseStates
import com.loyaltyglobal.data.source.network.manageApiDataByState
import com.loyaltyglobal.databinding.FragmentEnableNotificationBinding
import com.loyaltyglobal.ui.base.BaseFragment
import com.loyaltyglobal.ui.main.view.activity.MainActivity
import com.loyaltyglobal.ui.main.viewmodel.VerificationViewModel
import com.loyaltyglobal.util.clickWithDebounce
import com.loyaltyglobal.util.hide
import com.loyaltyglobal.util.show
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EnableNotificationFragment : BaseFragment() {
    private lateinit var mBinding: FragmentEnableNotificationBinding
    private val verificationViewModel: VerificationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initObserver()
    }

    private fun initObserver() {
        verificationViewModel.updateUserResponse.observe(this, {
            if (it != null) {
                manageApiDataByState(it, object : ApiResponseStates {
                    override fun onSuccess(responseData: Any?) {
                        mBinding.progressBar.hide()
                        startActivity(Intent(context, MainActivity::class.java))
                        activity?.finish()
                    }

                    override fun onLoading() = mBinding.progressBar.show()

                    override fun onError(codeData: Int?, message: String?) {
                        mBinding.progressBar.hide()
                    }
                })
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        mBinding = FragmentEnableNotificationBinding.inflate(layoutInflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.apply {
            txtSkip.clickWithDebounce {
                startActivity(Intent(context, MainActivity::class.java))
                activity?.finish()
            }
            imgLeftArrow.clickWithDebounce { activity?.supportFragmentManager?.popBackStack() }
            btnEnableNotification.clickWithDebounce {
                mPreferenceProvider?.getPlayerId()?.let { playerId -> verificationViewModel.enableNotification(playerId) }
            }
        }


    }

}
