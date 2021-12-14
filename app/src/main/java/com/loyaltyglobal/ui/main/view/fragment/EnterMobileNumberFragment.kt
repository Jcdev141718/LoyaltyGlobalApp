package com.loyaltyglobal.ui.main.view.fragment

import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.loyaltyglobal.R
import com.loyaltyglobal.data.model.CountryCodeData
import com.loyaltyglobal.databinding.FragmentEnterMobileNumberBinding
import com.loyaltyglobal.ui.main.viewmodel.OtpResponse
import com.loyaltyglobal.ui.main.viewmodel.VerificationViewModel
import com.loyaltyglobal.util.Constants
import com.loyaltyglobal.util.Constants.MINIMUM_LENGTH_OF_NUMBER
import com.loyaltyglobal.util.Constants.USER_NAME_KEY
import com.loyaltyglobal.util.addReplaceFragment
import com.loyaltyglobal.util.getCountryFlag
import com.loyaltyglobal.util.showToast


class EnterMobileNumberFragment : Fragment(), SendCountryCodeAndFlag {

    private val verificationViewModel: VerificationViewModel by viewModels()
    private lateinit var mBinding: FragmentEnterMobileNumberBinding
    private var userName : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            userName = it.getString(USER_NAME_KEY)
        }
        initObserver()
    }

    private fun initObserver() {
        verificationViewModel.otpResponse.observe(this, {
            if (it != null){
                when (it){
                    is OtpResponse.Success -> {
                        val bundle = Bundle().apply {
                            putString(Constants.MOBILE_NUMBER_KEY, verificationViewModel.mMobileNumber)
                        }
                        val verifyOTPFragment = VerifyOTPFragment().apply { arguments = bundle }
                        activity?.addReplaceFragment(
                            R.id.container_main, verifyOTPFragment, true,
                            addToBackStack = true
                        )
                    }
                    is OtpResponse.Error -> {
                        it.message?.let { it1 -> activity?.showToast(it1) }
                    }
                }
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentEnterMobileNumberBinding.inflate(layoutInflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        context?.let { verificationViewModel.getCurrentCountryData(it) }
        mBinding.txtWelcome.text = getString(R.string.hey_user_enter_your_phone_number, userName)
        mBinding.txtFlag.text = verificationViewModel.mCountryData?.key?.let { getCountryFlag(it) }
        mBinding.textCountryCode.text = verificationViewModel.mCountryData?.countryCode?: getString(R.string._41)
        setOnClickListener()
    }


    private fun setOnClickListener() {
        mBinding.editTextNumber.doOnTextChanged { text, _, _, _ ->
            if (text.toString().isNotEmpty() && text.toString().length == MINIMUM_LENGTH_OF_NUMBER){
                context?.let { ContextCompat.getColor(it, R.color.green) }
                    ?.let { mBinding.viewLineNumber.setBackgroundColor(it) }
            }else{
                val value = TypedValue()
                context?.theme?.resolveAttribute(R.attr.main_divider, value, true)
                mBinding.viewLineNumber.setBackgroundColor(value.data)
            }
        }
        mBinding.txtFlag.setOnClickListener {
            selectCountryCode()
        }
        mBinding.textCountryCode.setOnClickListener {
            selectCountryCode()
        }
        mBinding.imgBack.setOnClickListener {
            activity?.onBackPressed()
        }
        mBinding.clTxtNext.setOnClickListener {
            if (verificationViewModel.mCountryData?.countryCode.isNullOrEmpty()) {
                activity?.showToast(getString(R.string.please_select_country_code))
            } else {
                context?.let { it1 -> verificationViewModel.sendOtp(it1, mBinding.editTextNumber.text.toString()) }
            }
        }
    }

    override fun onCountryDataSelect(countryData: CountryCodeData) {
        verificationViewModel.mCountryData = countryData
        mBinding.textCountryCode.text = countryData.countryCode
        mBinding.txtFlag.text = getCountryFlag(countryData.key!!)

    }

    private fun selectCountryCode() {
        val mCountryListFragment = CountryListFragment()
        mCountryListFragment.mSendCountryCodeAndFlag = this
        activity?.addReplaceFragment(
            R.id.container_main,
            mCountryListFragment,
            addFragment = true,
            addToBackStack = true
        )
    }
}

interface SendCountryCodeAndFlag {
    fun onCountryDataSelect(countryData: CountryCodeData)
}
