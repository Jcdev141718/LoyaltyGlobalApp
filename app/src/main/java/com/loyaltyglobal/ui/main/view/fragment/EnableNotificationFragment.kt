package com.loyaltyglobal.ui.main.view.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.loyaltyglobal.databinding.FragmentEnableNotificationBinding
import com.loyaltyglobal.ui.main.view.activity.MainActivity

class EnableNotificationFragment : Fragment() {
    private lateinit var mBinding : FragmentEnableNotificationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentEnableNotificationBinding.inflate(layoutInflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.txtSkip.setOnClickListener {
            startActivity(Intent(context, MainActivity::class.java))
            activity?.finish()
        }
        mBinding.imgLeftArrow.setOnClickListener {
            activity?.onBackPressed()
        }
    }

}
