package com.loyaltyglobal.ui.main.view.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.loyaltyglobal.data.model.response.HomeScreenStoriesData
import com.loyaltyglobal.databinding.FragmentStoriesBinding
import com.loyaltyglobal.ui.main.adapter.StoriesAdapter
import com.loyaltyglobal.ui.main.viewmodel.HomeViewModel
import com.loyaltyglobal.util.RecyclerItemDecoration
import com.loyaltyglobal.util.dpToPx

@SuppressLint("NotifyDataSetChanged")
class StoriesFragment : Fragment() {

    lateinit var binding: FragmentStoriesBinding
    private var storiesAdapter: StoriesAdapter? = null
    private var mStoriesList = ArrayList<HomeScreenStoriesData>()
    private val homeViewModel: HomeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentStoriesBinding.inflate(inflater, container, false)
        initData()
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initObserver()
    }

    private fun initObserver() {
        homeViewModel.mStoriesList.observe(this, {
            if (!it.isNullOrEmpty()) {
                mStoriesList.clear()
                mStoriesList.addAll(it)
                storiesAdapter?.notifyDataSetChanged()
            }
        })
    }

    private fun initData() {
        storiesAdapter = StoriesAdapter(mStoriesList, object : StoriesAdapter.ClickListener {
            override fun itemClick(position: Int) {

            }
        })

        binding.apply {
            rvStories.layoutManager = GridLayoutManager(requireContext(), 2)
            rvStories.addItemDecoration(RecyclerItemDecoration(2, dpToPx(4f, resources)))
            rvStories.adapter = storiesAdapter
        }
    }

}
