package com.loyaltyglobal.ui.main.view.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.loyaltyglobal.R
import com.loyaltyglobal.databinding.ActivitySplashBinding
import com.loyaltyglobal.util.setImage
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    lateinit var mBinding : ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        mBinding.imgSplash.setImage(R.drawable.icon_logo)

        lifecycleScope.launch {
            delay(2000)
            startActivity(Intent(this@SplashActivity,LoginActivity::class.java))
            finish()
        }
    }
}
