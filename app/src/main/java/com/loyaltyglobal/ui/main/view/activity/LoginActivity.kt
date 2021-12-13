package com.loyaltyglobal.ui.main.view.activity

import android.os.Build
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.loyaltyglobal.R
import com.loyaltyglobal.databinding.ActivityLoginBinding
import com.loyaltyglobal.ui.main.view.fragment.LoginFragment
import com.loyaltyglobal.util.addReplaceFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
//        setSupportActionBar(null)
        addReplaceFragment(R.id.container_main,LoginFragment(),false, addToBackStack = false)
    }
}
