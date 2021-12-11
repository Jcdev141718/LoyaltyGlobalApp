package com.loyaltyglobal.ui.main.view.fragments

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.EditText
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.core.view.MenuItemCompat
import com.google.android.material.tabs.TabLayoutMediator
import com.loyaltyglobal.R
import com.loyaltyglobal.databinding.FragmentExploreBinding
import com.loyaltyglobal.ui.base.BaseFragment
import com.loyaltyglobal.ui.main.view.adapters.ViewPagerAdapter

class ExploreFragment : BaseFragment() {

    lateinit var mBinding: FragmentExploreBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentExploreBinding.inflate(layoutInflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = ViewPagerAdapter(requireActivity())
        mBinding.viewPager.adapter = adapter
        TabLayoutMediator(mBinding.tabsExplore, mBinding.viewPager) { tab, position ->
            if (position == 0) {
                tab.text = getString(R.string.explore_businesses)
            } else tab.text = getString(R.string.explore_deals_and_offers)
        }.attach()
    }
}
