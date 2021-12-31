package com.loyaltyglobal.ui.main.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.tabs.TabLayoutMediator
import com.loyaltyglobal.R
import com.loyaltyglobal.databinding.FragmentExploreBinding
import com.loyaltyglobal.ui.base.BaseFragment
import com.loyaltyglobal.ui.main.adapter.ViewPagerAdapter
import com.loyaltyglobal.util.addReplaceFragment

/**
 * Created by Abhin.
 */

class ExploreFragment : BaseFragment(), ExploreFilterFragment.ExploreFilterInterface {

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
        setExploreTabs()
        clickListener()
    }

    private fun clickListener() {
        mBinding.imgExploreFilter.setOnClickListener {
            val mExploreFilterFragment = ExploreFilterFragment()
            mExploreFilterFragment.mExploreFilterInterface = this
            hideBottomNavigation()
            activity?.addReplaceFragment(
                R.id.fl_main_container, mExploreFilterFragment,
                addFragment = true,
                addToBackStack = true
            )
        }
    }

    private fun setExploreTabs() {
        val adapter = ViewPagerAdapter(requireActivity())
        mBinding.viewPager.adapter = adapter
        TabLayoutMediator(mBinding.tabsExplore, mBinding.viewPager) { tab, position ->
            if (position == 0) {
                tab.text = getString(R.string.explore_businesses)
            } else tab.text = getString(R.string.explore_deals_and_offers)
        }.attach()
    }

    override fun applyFilter() {
        Toast.makeText(requireContext(), "Filter applied", Toast.LENGTH_SHORT).show()
    }
}
