package com.loyaltyglobal.ui.main.view.fragment

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.loyaltyglobal.R
import com.loyaltyglobal.databinding.FragmentProfileBinding
import com.loyaltyglobal.ui.base.AppTheme
import com.loyaltyglobal.ui.base.BaseFragment
import com.loyaltyglobal.ui.main.view.activity.MainActivity
import com.loyaltyglobal.util.*

/**
 * Created by Abhin.
 */

class ProfileFragment : BaseFragment() {

    private lateinit var mBinding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        mBinding = FragmentProfileBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnClickListener()
        setData()
    }

    private fun setOnClickListener() {
        mBinding.apply {
            when (resources.configuration.uiMode.and(Configuration.UI_MODE_NIGHT_MASK)) {
                Configuration.UI_MODE_NIGHT_NO -> switchDarkMode.isChecked = false
                Configuration.UI_MODE_NIGHT_YES -> switchDarkMode.isChecked = true
            }
            imgProfile.clickWithDebounce {

            }
            btnEdit.clickWithDebounce {
                openBottomSheet(EditProfileBottomSheetFragment())
            }
            llTransaction.clickWithDebounce {
                activity?.addReplaceFragment(R.id.fl_main_container, TransactionsFragment(), true, true)
            }
            txtLogout.clickWithDebounce {
                activity?.showTopSnackBar("", "message")
            }
            switchDarkMode.setOnCheckedChangeListener { button, isChecked ->
                if (button.isPressed) {
                    if (isChecked) {
                        mBaseActivity?.setTheme(AppTheme.Dark)
                        activity?.finish()
                        startActivity(Intent(activity, MainActivity::class.java))
                    } else {
                        mBaseActivity?.setTheme(AppTheme.Light)
                        activity?.finish()
                        startActivity(Intent(activity, MainActivity::class.java))
                    }
                }
            }
        }

    }

    private fun setData() {
        mPreferenceProvider?.getUserData()?.let { userData ->
            mBinding.txtName.text =
                TextUtils.concat(userData.firstName?.firstLetterCap(), " ", userData.lastName?.firstLetterCap())
            mBinding.txtEmail.text = userData.email
        }
    }


}
