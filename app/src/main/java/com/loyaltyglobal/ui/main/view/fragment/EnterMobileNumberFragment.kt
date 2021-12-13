package com.loyaltyglobal.ui.main.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.loyaltyglobal.R
import com.loyaltyglobal.data.model.CountryCodeData
import com.loyaltyglobal.databinding.FragmentEnterMobileNumberBinding
import com.loyaltyglobal.util.Constants.MOBILE_NUMBER_KEY
import com.loyaltyglobal.util.addReplaceFragment
import com.loyaltyglobal.util.getCountryFlag
import com.loyaltyglobal.util.setImage


class EnterMobileNumberFragment : Fragment(), SendCountryCodeAndFlag {

    private lateinit var mBinding: FragmentEnterMobileNumberBinding
    private var mCountryData: CountryCodeData = CountryCodeData()

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

        mBinding.txtFlag.text = mCountryData.key?.let { getCountryFlag(it) }
        setOnClickListener()
    }

    private fun setOnClickListener() {
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
            when {
                mCountryData.countryCode.isNullOrEmpty() -> {
                    Toast.makeText(
                        context,
                        getString(R.string.please_select_contry_code),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                mBinding.editTextNumber.text.toString().isEmpty() -> {
                    Toast.makeText(
                        context,
                        getString(R.string.please_enter_mobile_number),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {
                    val bundle = Bundle().apply {
                        putString(MOBILE_NUMBER_KEY, "${mCountryData.countryCode}${mBinding.editTextNumber.text.toString()}")
                    }
                    val verifyOTPFragment = VerifyOTPFragment().apply { arguments = bundle }
                    activity?.addReplaceFragment(
                        R.id.container_main, verifyOTPFragment, true,
                        addToBackStack = true
                    )
                }
            }
        }
    }

    override fun onCountryDataSelect(countryData: CountryCodeData) {
        mCountryData = countryData
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
