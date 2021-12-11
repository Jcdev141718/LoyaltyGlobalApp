package com.loyaltyglobal.ui.base

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.loyaltyglobal.ui.main.view.activity.SplashActivity

/**
 * Created by Abhin.
 */

class IntroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        intent = Intent(
            this,
            SplashActivity::class.java
        )
        startActivity(intent)
        finish()
    }
}
