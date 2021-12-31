package com.loyaltyglobal.ui.main.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.loyaltyglobal.databinding.FragmentExploreAllDetailsBinding
import com.loyaltyglobal.ui.main.adapter.LinksAdapter
import com.loyaltyglobal.ui.main.viewmodel.ExploreViewModel
import com.loyaltyglobal.util.clickWithDebounce
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by Abhin.
 */

@AndroidEntryPoint
class ExploreAllDetailsFragment : Fragment() {

    private val exploreViewModel: ExploreViewModel by activityViewModels()
    private lateinit var binding : FragmentExploreAllDetailsBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentExploreAllDetailsBinding.inflate(inflater,container,false).apply {
            viewModel = exploreViewModel
            lifecycleOwner = this@ExploreAllDetailsFragment
            executePendingBindings()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        binding.imgBack.clickWithDebounce { activity?.supportFragmentManager?.popBackStack() }
    }

    private fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvLinks.layoutManager = layoutManager
        val linkAdapter = LinksAdapter(arrayListOf(), object : LinksAdapter.LinkClickListeners {
            override fun onClick(position: Int) {
                //TODO : Open webView
                /*activity?.addReplaceFragment(R.id.fl_main_container, WebViewFragment().apply {
                    arguments = Bundle().apply {
                        putString(Constants.KEY_WEB_URL, mHomeScreenDealPromotionsList[position].value)
                    }
                }, true, true)
                (activity as MainActivity).showHideBottomNavigationBar(false)*/
            }
        })
        binding.rvLinks.adapter = linkAdapter
    }
}