package com.loyaltyglobal.ui.main.view.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import com.loyaltyglobal.R
import com.loyaltyglobal.databinding.FragmentVerifyOTPBinding
import com.loyaltyglobal.ui.main.view.activity.MainActivity
import com.loyaltyglobal.util.addReplaceFragment
import com.loyaltyglobal.util.hideKeyboard
import com.loyaltyglobal.util.showKeyboard


class VerifyOTPFragment : Fragment() {
    private lateinit var mBinding : FragmentVerifyOTPBinding
    private var finalOTP = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

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
        setOnClickListener()

    }

    private fun setOnClickListener() {
        mBinding.imgBack.setOnClickListener {
            activity?.onBackPressed()
        }
        mBinding.otp1.doOnTextChanged { text, _, _, _ ->
            if (text.toString().isNotEmpty()){
                finalOTP += text.toString()
                mBinding.otp2.requestFocus()
            }
        }
        mBinding.otp2.doOnTextChanged { text, _, _, _ ->
            if (text.toString().isNotEmpty()){
                finalOTP += text.toString()
                mBinding.otp3.requestFocus()
            }else{
                mBinding.otp1.requestFocus()
            }
        }
        mBinding.otp3.doOnTextChanged { text, _, _, _ ->
            if (text.toString().isNotEmpty()){
                finalOTP += text.toString()
                mBinding.otp4.requestFocus()
            }
            else{
                mBinding.otp2.requestFocus()
            }
        }
        mBinding.otp4.doOnTextChanged { text, _, _, _ ->
            if (text.toString().isNotEmpty()){
                finalOTP += text.toString()
                mBinding.otp5.requestFocus()
            }else{
                mBinding.otp3.requestFocus()
            }
        }
        mBinding.otp5.doOnTextChanged { text, _, _, _ ->
            if (text.toString().isNotEmpty()){
                finalOTP += text.toString()
                mBinding.otp6.requestFocus()
            }
            else{
                mBinding.otp4.requestFocus()
            }
        }
        mBinding.otp6.doOnTextChanged { text, _, _, _ ->
            if (text.toString().isNotEmpty()){
                activity?.hideKeyboard()
                finalOTP += text.toString()
                if (finalOTP.equals("444444",true)){
                    activity?.addReplaceFragment(R.id.container_main, EnterNameFragment(), true,
                        addToBackStack = true
                    )
                }else{
                    Toast.makeText(context, getString(R.string.invalid_otp), Toast.LENGTH_SHORT).show()
                }
            }else{
                mBinding.otp5.requestFocus()
                activity?.showKeyboard()
            }
        }
    }


}
