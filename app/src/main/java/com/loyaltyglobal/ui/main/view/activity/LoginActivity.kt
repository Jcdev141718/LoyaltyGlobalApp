package com.loyaltyglobal.ui.main.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.loyaltyglobal.R
import com.loyaltyglobal.databinding.ActivityLoginBinding
import com.loyaltyglobal.ui.main.view.fragment.ExploreDetailsFragment
import com.loyaltyglobal.ui.main.view.fragment.LoginFragment
import com.loyaltyglobal.util.addReplaceFragment

class LoginActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        addReplaceFragment(R.id.container_main, ExploreDetailsFragment(),true, addToBackStack = true)
        //addReplaceFragment(R.id.container_main,LoginFragment(),true, addToBackStack = true)
    }
}
