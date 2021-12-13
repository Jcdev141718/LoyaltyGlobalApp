package com.loyaltyglobal.ui.main.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import com.google.firebase.auth.PhoneAuthProvider
import com.loyaltyglobal.R
import com.loyaltyglobal.databinding.FragmentVerifyOTPBinding
import com.loyaltyglobal.util.Constants.MOBILE_NUMBER_KEY
import com.loyaltyglobal.util.PhoneAuthHelper
import com.loyaltyglobal.util.addReplaceFragment
import com.loyaltyglobal.util.hideKeyboard
import com.loyaltyglobal.util.showKeyboard


class VerifyOTPFragment : Fragment(), PhoneAuthHelper.PhoneAuthInterface {
    private lateinit var mBinding: FragmentVerifyOTPBinding
    private lateinit var phoneNumber: String
    private var finalOTP = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            phoneNumber = it.getString(MOBILE_NUMBER_KEY, "")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentVerifyOTPBinding.inflate(layoutInflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        PhoneAuthHelper.mPhoneAuthInterface = this
//        activity?.let { PhoneAuthHelper.getNUmber(it, phoneNumber) }
        setOnClickListener()

    }

    private fun setOnClickListener() {
        mBinding.txtResendCode.setOnClickListener {
            //TODO: Enable this code in final
//            activity?.let { it1 -> PhoneAuthHelper.resendVerificationCode(it1, phoneNumber) }
        }
        mBinding.imgBack.setOnClickListener {
            activity?.onBackPressed()
        }
        mBinding.otp1.doOnTextChanged { text, _, _, _ ->
            if (text.toString().isNotEmpty()) {
                finalOTP += text.toString()
                mBinding.otp2.requestFocus()
            }
        }
        mBinding.otp2.doOnTextChanged { text, _, _, _ ->
            if (text.toString().isNotEmpty()) {
                finalOTP += text.toString()
                mBinding.otp3.requestFocus()
            } else {
                mBinding.otp1.requestFocus()
            }
        }
        mBinding.otp3.doOnTextChanged { text, _, _, _ ->
            if (text.toString().isNotEmpty()) {
                finalOTP += text.toString()
                mBinding.otp4.requestFocus()
            } else {
                mBinding.otp2.requestFocus()
            }
        }
        mBinding.otp4.doOnTextChanged { text, _, _, _ ->
            if (text.toString().isNotEmpty()) {
                finalOTP += text.toString()
                mBinding.otp5.requestFocus()
            } else {
                mBinding.otp3.requestFocus()
            }
        }
        mBinding.otp5.doOnTextChanged { text, _, _, _ ->
            if (text.toString().isNotEmpty()) {
                finalOTP += text.toString()
                mBinding.otp6.requestFocus()
            } else {
                mBinding.otp4.requestFocus()
            }
        }
        mBinding.otp6.doOnTextChanged { text, _, _, _ ->
            if (text.toString().isNotEmpty()) {
                activity?.hideKeyboard()
                finalOTP += text.toString()
                finalVerification(finalOTP)
            } else {
                mBinding.otp5.requestFocus()
                activity?.showKeyboard()
            }
        }
    }

    private fun finalVerification(otp: String) {
        if (otp.length == 6) {
            //TODO: enable this code in final code
//            PhoneAuthHelper.signInWithPhoneAuthCredential(otp)

            //TODo: Remove this code in final
            activity?.addReplaceFragment(
                R.id.container_main, EnterNameFragment(), true,
                addToBackStack = true
            )
        }
    }

    override fun otpVerificationSuccess() {
        activity?.addReplaceFragment(
            R.id.container_main, EnterNameFragment(), true,
            addToBackStack = true
        )
    }

    override fun otpVerificationFailed(message: String?) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun onVerificationCodeSent(
        verificationId: String,
        resendToken: PhoneAuthProvider.ForceResendingToken
    ) {

    }


}
