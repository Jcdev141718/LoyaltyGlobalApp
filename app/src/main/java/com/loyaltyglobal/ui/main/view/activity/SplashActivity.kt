package com.loyaltyglobal.ui.main.view.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.loyaltyglobal.R
import com.loyaltyglobal.databinding.ActivitySplashBinding
import com.loyaltyglobal.util.Constants.IS_USER_LOGIN_KEY
import com.loyaltyglobal.util.PreferenceProvider
import com.loyaltyglobal.util.setImage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    @Inject
    lateinit var preferenceProvider: PreferenceProvider

    lateinit var mBinding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivitySplashBinding.inflate(layoutInflater)
        window.statusBarColor = ContextCompat.getColor(this, R.color.black)
        setContentView(mBinding.root)
        mBinding.imgSplash.setImage(R.drawable.img_splash)

        lifecycleScope.launch {
            delay(1000)
            if (preferenceProvider.getValue(IS_USER_LOGIN_KEY, false)){
                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            }else{
                startActivity(Intent(this@SplashActivity, LoginActivity/*MainActivity*/::class.java))
            }
            finish()
        }
    }
}
