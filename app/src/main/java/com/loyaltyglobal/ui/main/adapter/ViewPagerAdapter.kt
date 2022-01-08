package com.loyaltyglobal.ui.main.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.loyaltyglobal.ui.main.view.fragment.BusinessFragment
import com.loyaltyglobal.ui.main.view.fragment.DealsAndOffersFragment

/**
 * Created by Abhin.
 */
class ViewPagerAdapter(
    supportFragmentManager: FragmentActivity,
    var businessFragment: BusinessFragment
) :
    FragmentStateAdapter(supportFragmentManager) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> businessFragment
            1 -> DealsAndOffersFragment()
            else -> businessFragment
        }
    }
}
