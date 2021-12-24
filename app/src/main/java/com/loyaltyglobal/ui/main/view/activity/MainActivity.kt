package com.loyaltyglobal.ui.main.view.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.loyaltyglobal.R
import com.loyaltyglobal.databinding.ActivityMainBinding
import com.loyaltyglobal.notifications.NotificationReceiveListener
import com.loyaltyglobal.notifications.NotificationServiceExtension
import com.loyaltyglobal.ui.main.view.fragment.ExploreFragment
import com.loyaltyglobal.ui.main.view.fragment.HomeScreenFragment
import com.loyaltyglobal.ui.main.view.fragment.ProfileFragment
import com.loyaltyglobal.ui.main.view.fragment.StoriesFragment
import com.loyaltyglobal.ui.main.viewmodel.HomeViewModel
import com.loyaltyglobal.util.addReplaceFragment
import com.loyaltyglobal.util.hide
import com.loyaltyglobal.util.show
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), NotificationReceiveListener {

    private val homeViewModel: HomeViewModel by viewModels()
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadHomeFragment()
        clickListener()
        NotificationServiceExtension.mNotificationReceiveListener = this
        lifecycleScope.launch {
            if (!homeViewModel.isDataIsAvailableInDB()) {
                homeViewModel.getSubBrands()
                homeViewModel.getUserPassFromAgency()
            }
        }
    }

    private fun clickListener() {
        binding.menuBottom.setOnItemSelectedListener {
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
                R.id.menu_stories -> {
                    addReplaceFragment(
                        R.id.fl_main_container, StoriesFragment(),
                        addFragment = true,
                        addToBackStack = false
                    )
                }
                R.id.menu_profile -> {
                    addReplaceFragment(
                        R.id.fl_main_container, ProfileFragment(),
                        addFragment = true,
                        addToBackStack = false
                    )
                }
            }
        }
    }

    private fun loadHomeFragment() {
        addReplaceFragment(
            R.id.fl_main_container, HomeScreenFragment(),
            addFragment = true,
            addToBackStack = false
        )
        binding.menuBottom.setItemSelected(R.id.menu_home)
    }

    override fun onNotificationReceive(payload: String) {

    }

    fun showHideBottomNavigationBar(isShow : Boolean){
        if (isShow) {
            binding.menuBottom.show()
        } else {
            binding.menuBottom.hide()
        }
    }

    fun moveToStoriesTab() {
        binding.menuBottom.setItemSelected(R.id.menu_stories)
    }
}
