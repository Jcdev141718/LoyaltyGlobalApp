package com.loyaltyglobal.ui.main.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.loyaltyglobal.R
import com.loyaltyglobal.databinding.FragmentLoginBinding
import com.loyaltyglobal.util.addReplaceFragment

class LoginFragment : Fragment() {

    lateinit var mBinding: FragmentLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentLoginBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.clTxtNext.setOnClickListener {
            activity?.addReplaceFragment(R.id.container_main, EnterMobileNumberFragment(), true,
                addToBackStack = true
            )
        }
    }

}
