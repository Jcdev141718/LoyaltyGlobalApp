package com.loyaltyglobal.ui.main.view.fragment

import android.os.Bundle
import android.text.TextUtils.concat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.loyaltyglobal.R
import com.loyaltyglobal.data.model.UpdateProfileEvent
import com.loyaltyglobal.databinding.FragmentEditProfileBinding
import com.loyaltyglobal.ui.base.BaseFragment
import com.loyaltyglobal.util.*
import dagger.hilt.android.AndroidEntryPoint
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.*
import javax.inject.Inject

/**
 * Created by Abhin.
 */

@AndroidEntryPoint
class EditProfileFragment : BaseFragment() {

    private lateinit var binding: FragmentEditProfileBinding

    @Inject
    lateinit var preferenceProvider: PreferenceProvider

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?,
    ): View {
        binding = FragmentEditProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setDataToUI()
        initClicks()
    }

    private fun initClicks() {
        binding.imgClose.clickWithDebounce { activity?.popFragment() }
        binding.txtSave.clickWithDebounce { activity?.popFragment() }
        binding.txtName.clickWithDebounce {
            activity?.addReplaceFragment(R.id.fl_main_container, EnterNameFragment().apply {
                arguments = Bundle().apply {
                    putBoolean(Constants.KEY_IS_FROM_EDIT_PROFILE, true)
                }
            }, true, true)
        }
        binding.txtDob.clickWithDebounce {
            activity?.addReplaceFragment(R.id.fl_main_container,DateOfBirthFragment(),true,true)
        }
    }

    private fun setDataToUI() {
        preferenceProvider.getUserData().let { userData ->
            binding.apply {
                txtName.text = concat(userData.firstName, " ", userData.lastName)
                txtEmailAddress.text = userData.email
                val date = Date().apply {
                    time = userData.birthday!!
                }
                txtDob.text = convertDateYearFormat(date)
                txtPhoneNum.text = concat(userData.dialingCode, userData.phone)
            }
        }
    }


    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onUpdateProfileEvent(event: UpdateProfileEvent?) {
        if (event?.isUpdate == true) {
            setDataToUI()
        }
    }

}