package com.loyaltyglobal.ui.main.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import com.loyaltyglobal.R
import com.loyaltyglobal.databinding.FragmentEnterNameBinding
import com.loyaltyglobal.util.*
import com.loyaltyglobal.util.Constants.IS_USER_LOGIN_KEY
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class EnterNameFragment : Fragment() {

    @Inject
    lateinit var preferenceProvider: PreferenceProvider

    private lateinit var mBinding: FragmentEnterNameBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentEnterNameBinding.inflate(layoutInflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClickListener()
    }

    private fun setOnClickListener() {
        mBinding.editTextName.doOnTextChanged { text, _, _, _ ->
            if (text.toString().isNotEmpty()) {
                mBinding.clTxtNext.show()
            } else {
                mBinding.clTxtNext.hide()
            }
        }
        mBinding.clTxtNext.setOnClickListener {
            if (mBinding.editTextName.text.toString().isEmpty()) {
                activity?.showTopSnackBar(
                    getString(R.string.error),
                    getString(R.string.please_enter_name)
                )
            } else {
                preferenceProvider.setValue(IS_USER_LOGIN_KEY, true)
                activity?.addReplaceFragment(
                    R.id.container_main,
                    EnableNotificationFragment(),
                    addFragment = true,
                    addToBackStack = true
                )
            }
        }
    }

    override fun onResume() {
        super.onResume()

    }
}
