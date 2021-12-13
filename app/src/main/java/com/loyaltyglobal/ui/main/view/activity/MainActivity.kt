package com.loyaltyglobal.ui.main.view.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.loyaltyglobal.R
import com.loyaltyglobal.ui.main.view.fragment.HomeScreenFragment
import com.loyaltyglobal.databinding.ActivityMainBinding
import com.loyaltyglobal.ui.main.view.fragment.ExploreFragment
import com.loyaltyglobal.ui.main.viewmodel.HomeViewModel
import com.loyaltyglobal.util.addReplaceFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val homeViewModel: HomeViewModel by viewModels()
    lateinit var mainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)
        loadHomeFragment()
        clickListener()
//        homeViewModel.getSubBrands()
//        homeViewModel.getUserPassFromAgency()
    }

    private fun clickListener() {
        mainBinding.menuBottom.setOnItemSelectedListener {
            when (it) {
                R.id.menu_home -> {
                    addReplaceFragment(
                        R.id.fl_main_container, HomeScreenFragment(),
                        addFragment = true,
                        addToBackStack = false
                    )
                }
                R.id.menu_explore -> {
                    addReplaceFragment(
                        R.id.fl_main_container, ExploreFragment(),
                        addFragment = true,
                        addToBackStack = false
                    )
                }
                R.id.menu_star -> {}
                R.id.menu_user -> {}
            }
        }
    }

    private fun loadHomeFragment() {
        addReplaceFragment(
            R.id.fl_main_container, HomeScreenFragment(),
            addFragment = true,
            addToBackStack = false
        )
        mainBinding.menuBottom.setItemSelected(R.id.menu_home)
    }


}
