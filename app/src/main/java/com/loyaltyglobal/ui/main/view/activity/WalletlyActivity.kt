package com.loyaltyglobal.ui.main.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.loyaltyglobal.R
import com.loyaltyglobal.databinding.ActivityMainBinding
import com.loyaltyglobal.databinding.ActivityWalletlyBinding

class WalletlyActivity : AppCompatActivity() {

    lateinit var binding: ActivityWalletlyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWalletlyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setData()

    }

    private fun setData() {
        binding.apply {

        }
    }
}
