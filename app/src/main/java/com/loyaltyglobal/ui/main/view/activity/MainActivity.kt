package com.loyaltyglobal.ui.main.view.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.loyaltyglobal.R
import com.loyaltyglobal.ui.main.view.fragment.HomeScreenFragment
import com.loyaltyglobal.ui.main.viewmodel.HomeViewModel
import com.loyaltyglobal.util.addReplaceFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val homeViewModel : HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        addReplaceFragment(
            R.id.fl_container,
            HomeScreenFragment(),
            addFragment = false,
            addToBackStack = false
        )

//        homeViewModel.getSubBrands()
//        homeViewModel.getUserPassFromAgency()
    }
}
