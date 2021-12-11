package com.loyaltyglobal.ui.main.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.loyaltyglobal.R
import com.loyaltyglobal.ui.main.view.fragment.LoginFragment
import com.loyaltyglobal.util.addReplaceFragment

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        addReplaceFragment(
            R.id.container_main, LoginFragment(),
            true,
            addToBackStack = true
        )
    }
}