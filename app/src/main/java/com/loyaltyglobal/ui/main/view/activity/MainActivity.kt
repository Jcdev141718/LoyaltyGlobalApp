package com.loyaltyglobal.ui.main.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.loyaltyglobal.R
import com.loyaltyglobal.ui.main.viewmodel.HomeViewModel

class MainActivity : AppCompatActivity() {

    private val homeViewModel : HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        homeViewModel.getSubBrands()
    }
}
