package com.loyaltyglobal.ui.main.view.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

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
