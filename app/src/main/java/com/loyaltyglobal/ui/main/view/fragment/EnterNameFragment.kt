package com.loyaltyglobal.ui.main.view.fragment

import android.os.Bundle
import android.text.TextUtils.concat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import com.google.gson.Gson
import com.loyaltyglobal.R
import com.loyaltyglobal.data.model.UpdateProfileEvent
import com.loyaltyglobal.databinding.FragmentEnterNameBinding
import com.loyaltyglobal.ui.base.BaseFragment
import com.loyaltyglobal.util.*
import com.loyaltyglobal.util.Constants.IS_USER_LOGIN_KEY
import com.loyaltyglobal.util.Constants.KEY_IS_FROM_EDIT_PROFILE
import dagger.hilt.android.AndroidEntryPoint
import org.greenrobot.eventbus.EventBus




/**
 * Created by Abhin.
 */

@AndroidEntryPoint
class EnterNameFragment : BaseFragment() {

    private lateinit var binding: FragmentEnterNameBinding
    private var isFromEditProfile: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getBoolean(KEY_IS_FROM_EDIT_PROFILE)?.let { isFromEditProfile = it }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentEnterNameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        setOnClickListener()
    }

    private fun initUI() {
        if (isFromEditProfile) {
            mPreferenceProvider?.getUserData()?.let {
                binding.editTextName.setText(concat(it.firstName," ",it.lastName))
            }
            binding.txtNext.text = getString(R.string.update)
        } else {
            binding.txtNext.text = getString(R.string.next)
        }
    }

    private fun setOnClickListener() {
        binding.editTextName.doOnTextChanged { text, _, _, _ ->
            if (text.toString().isNotEmpty()) {
                binding.clTxtNext.show()
            } else {
                binding.clTxtNext.hide()
            }
        }
        binding.clTxtNext.clickWithDebounce {
            if (binding.editTextName.text.toString().isEmpty()) {
                activity?.showTopSnackBar(getString(R.string.error), getString(R.string.please_enter_name))
            } else {
                if (isFromEditProfile) {
                    mPreferenceProvider?.apply {
                        val data = getUserData()
                        data.firstName = binding.editTextName.text.toString()
                        data.lastName = ""
                        setValue(Constants.PREF_USER_DATA, Gson().toJson(data))
                        EventBus.getDefault().post(UpdateProfileEvent(true))
                    }
                    activity?.hideKeyboard()
                    activity?.popFragment()
                } else {
                    mPreferenceProvider?.setValue(IS_USER_LOGIN_KEY, true)
                    activity?.addReplaceFragment(R.id.container_main, EnableNotificationFragment(), addFragment = true, addToBackStack = true)
                }
            }
        }
    }
}
