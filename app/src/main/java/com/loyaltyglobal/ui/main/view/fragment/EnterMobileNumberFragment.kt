package com.loyaltyglobal.ui.main.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.loyaltyglobal.R
import com.loyaltyglobal.data.model.CountryCodeData
import com.loyaltyglobal.databinding.FragmentEnterMobileNumberBinding
import com.loyaltyglobal.util.addReplaceFragment
import com.loyaltyglobal.util.getCountryFlag
import com.loyaltyglobal.util.setImage
import kotlinx.coroutines.flow.combine


class EnterMobileNumberFragment : Fragment(), SendCountryCodeAndFlag {

    private lateinit var mBinding: FragmentEnterMobileNumberBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
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
        setOnClickListener()
    }

    private fun setOnClickListener() {
        mBinding.imgFlag.setOnClickListener {
            selectCountryCode()
        }
        mBinding.textCountryCode.setOnClickListener {
            selectCountryCode()
        }
        mBinding.imgBack.setOnClickListener {
            activity?.onBackPressed()
        }
        mBinding.clTxtNext.setOnClickListener {
            activity?.addReplaceFragment(R.id.container_main, VerifyOTPFragment(), true,
                addToBackStack = true
            )
        }
    }

    override fun onCountryDataSelect(countryData: CountryCodeData) {
        mBinding.textCountryCode.text = countryData.countryCode
        mBinding.imgFlag.setImage(getCountryFlag(countryData.key!!))

    }

    private fun selectCountryCode() {
        val mCountryListFragment = CountryListFragment()
        mCountryListFragment.mSendCountryCodeAndFlag = this
        activity?.addReplaceFragment(R.id.container_main, mCountryListFragment, addFragment = true, addToBackStack = true)
    }

}

interface SendCountryCodeAndFlag {
    fun onCountryDataSelect(countryData: CountryCodeData)
}
