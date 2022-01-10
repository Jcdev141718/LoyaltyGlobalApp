package com.loyaltyglobal.ui.main.view.fragment

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.loyaltyglobal.R
import com.loyaltyglobal.databinding.FragmentProfileBinding
import com.loyaltyglobal.ui.base.BaseFragment
import com.loyaltyglobal.util.*

/**
 * Created by Abhin.
 */

class ProfileFragment : BaseFragment() {

    private lateinit var mBinding: FragmentProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
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
            imgProfile.clickWithDebounce {

            }
            btnEdit.clickWithDebounce {
                openBottomSheet(EditProfileBottomSheetFragment())
            }
            llTransaction.clickWithDebounce {
                activity?.addReplaceFragment(R.id.fl_main_container, TransactionsFragment(), true, true)
            }
            txtLogout.clickWithDebounce {
                activity?.showTopSnackBar("title","message")
            }
            switchDarkMode.setOnCheckedChangeListener { _, isChecked ->

            }
        }

    }
    private fun setData(){
        mPreferenceProvider?.getUserData()?.let { userData ->
            mBinding.txtName.text = TextUtils.concat(userData.firstName?.firstLetterCap()," ", userData.lastName?.firstLetterCap())
            mBinding.txtEmail.text = userData.email
        }
    }


}
