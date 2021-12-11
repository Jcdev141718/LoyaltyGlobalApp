package com.loyaltyglobal.ui.main.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.loyaltyglobal.R
import com.loyaltyglobal.databinding.FragmentVerifyOTPBinding
import com.loyaltyglobal.util.addReplaceFragment


class VerifyOTPFragment : Fragment() {
    private lateinit var mBinding : FragmentVerifyOTPBinding
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
        mBinding.otpView.setOtpCompletionListener {
            if (it.equals("444444")){
                activity?.addReplaceFragment(R.id.main_container, EnterNameFragment(), true,
                    addToBackStack = true
                )
            }
        }

    }


}
