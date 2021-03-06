package com.loyaltyglobal.ui.main.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.loyaltyglobal.databinding.FragmentProfileBinding
import com.loyaltyglobal.ui.base.BaseFragment
import com.loyaltyglobal.util.clickWithDebounce


class ProfileFragment : BaseFragment() {

    private lateinit var mBinding: FragmentProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentProfileBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnClickListener()
    }

    private fun setOnClickListener() {
        mBinding.apply {
            imgProfile.clickWithDebounce {

            }
            btnEdit.clickWithDebounce {

            }
            llTransaction.clickWithDebounce {

            }
            txtLogout.clickWithDebounce {

            }
            switchDarkMode.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked){
                    //TODO: Enable dark mode here
                }else{
                    //TODO: Disable dark mode here
                }
            }
        }

    }


}
