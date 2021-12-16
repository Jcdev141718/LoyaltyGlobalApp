package com.loyaltyglobal.ui.main.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import com.google.firebase.auth.PhoneAuthProvider
import com.loyaltyglobal.R
import com.loyaltyglobal.databinding.FragmentVerifyOTPBinding
import com.loyaltyglobal.util.*
import com.loyaltyglobal.util.Constants.MOBILE_NUMBER_KEY
import com.loyaltyglobal.util.Constants.OTP_LENGTH


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
        activity?.let { PhoneAuthHelper.getNUmber(it, phoneNumber) }
        setOnClickListener()

    }

    private fun setOnClickListener() {
        mBinding.apply {
            txtResendCode.setOnClickListener {
                //TODO: Enable this code in final
            activity?.let { it1 -> PhoneAuthHelper.resendVerificationCode(it1, phoneNumber) }
            }
            imgBack.setOnClickListener { activity?.onBackPressed() }
            otp1.doOnTextChanged { text, _, _, _ ->
                if (text.toString().isNotEmpty()) {
                    otp2.requestFocus()
                }
                getFinalOtp()
            }
            otp2.doOnTextChanged { text, _, _, _ ->

                if (text.toString().isNotEmpty()) {
                    otp3.requestFocus()
                } else {
                    otp1.requestFocus()
                }
                getFinalOtp()
            }
            otp3.doOnTextChanged { text, _, _, _ ->
                if (text.toString().isNotEmpty()) {
                    otp4.requestFocus()
                } else {
                    otp2.requestFocus()
                }
                getFinalOtp()
            }
            otp4.doOnTextChanged { text, _, _, _ ->
                if (text.toString().isNotEmpty()) {
                    otp5.requestFocus()
                } else {
                    otp3.requestFocus()
                }
                getFinalOtp()
            }
            otp5.doOnTextChanged { text, _, _, _ ->
                if (text.toString().isNotEmpty()) {
                    otp6.requestFocus()
                } else {
                    otp4.requestFocus()
                }
                getFinalOtp()
            }
            otp6.doOnTextChanged { text, _, _, _ ->
                if (text.toString().isNotEmpty()) {
                    activity?.hideKeyboard()
                } else {
                    otp5.requestFocus()
                    activity?.showKeyboard()
                }
                getFinalOtp()
            }
        }
    }

    private fun getFinalOtp() {
        finalOTP = ""
        mBinding.apply {
            finalOTP += otp1.text.toString()
            finalOTP += otp2.text.toString()
            finalOTP += otp3.text.toString()
            finalOTP += otp4.text.toString()
            finalOTP += otp5.text.toString()
            finalOTP += otp6.text.toString()
        }
        if (finalOTP.length == OTP_LENGTH) {
            finalVerification(finalOTP)
        }
    }

    private fun finalVerification(otp: String) {
        if (otp.length == OTP_LENGTH) {
            //TODO: enable this code in final code
            PhoneAuthHelper.signInWithPhoneAuthCredential(otp)

            //TODo: Remove this code in final
//            activity?.addReplaceFragment(
//                R.id.container_main, EnterNameFragment(), true,
//                addToBackStack = true
//            )
        }
    }

    override fun otpVerificationSuccess() {
        activity?.addReplaceFragment(
            R.id.container_main, EnterNameFragment(), true,
            addToBackStack = true
        )
    }

    override fun otpVerificationFailed(message: String?) {
        message?.let { activity?.showTopSnackBar(getString(R.string.error),it) }
    }

    override fun onVerificationCodeSent(
        verificationId: String,
        resendToken: PhoneAuthProvider.ForceResendingToken
    ) {

    }


}
