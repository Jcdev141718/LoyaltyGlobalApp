package com.loyaltyglobal.ui.main.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayoutMediator
import com.loyaltyglobal.R
import com.loyaltyglobal.databinding.FragmentExploreBinding
import com.loyaltyglobal.ui.base.BaseFragment
import com.loyaltyglobal.ui.main.adapter.ViewPagerAdapter
import com.loyaltyglobal.util.addReplaceFragment
import com.loyaltyglobal.util.clickWithDebounce

/**
 * Created by Abhin.
 */

class ExploreFragment : BaseFragment() {

    lateinit var mBinding: FragmentExploreBinding
    private var switchBusinessMap = false
    private var businessFragment = BusinessFragment()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        mBinding = FragmentExploreBinding.inflate(layoutInflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setExploreTabs()
        clickListener()
    }

    private fun clickListener() {
        mBinding.imgExploreFilter.setOnClickListener {
            hideBottomNavigation()
            activity?.addReplaceFragment(R.id.fl_main_container, ExploreFilterFragment(), addFragment = true, addToBackStack = true)
        }
        mBinding.imgExploreSearch.clickWithDebounce {
            hideBottomNavigation()
            activity?.addReplaceFragment(R.id.fl_main_container, SearchBrandsFragment(), addFragment = true, addToBackStack = true)
        }

        mBinding.imgMap.setOnClickListener {
            switchBusinessMap = !switchBusinessMap
            if (switchBusinessMap) {
                mBinding.imgMap.setImageResource(R.drawable.ic_menu)
            } else mBinding.imgMap.setImageResource(R.drawable.ic_map)
            mBinding.viewPager.isUserInputEnabled = !switchBusinessMap
            mBinding.viewPager.setCurrentItem(0, true)
            businessFragment.switchMap(switchBusinessMap)
        }
    }

    private fun setExploreTabs() {
        val adapter = ViewPagerAdapter(requireActivity(), businessFragment)
        mBinding.viewPager.adapter = adapter
        TabLayoutMediator(mBinding.tabsExplore, mBinding.viewPager) { tab, position ->
            if (position == 0) {
                tab.text = getString(R.string.explore_businesses)
            } else tab.text = getString(R.string.explore_deals_and_offers)
        }.attach()
    }
}
