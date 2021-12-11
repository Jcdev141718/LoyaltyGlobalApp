package com.loyaltyglobal.ui.main.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.loyaltyglobal.databinding.FragmentStoriesBinding
import com.loyaltyglobal.ui.main.adapter.StoriesAdapter
import com.loyaltyglobal.util.RecyclerItemDecoration
import com.loyaltyglobal.util.dpToPx


class StoriesFragment : Fragment() {

    lateinit var binding: FragmentStoriesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStoriesBinding.inflate(inflater, container, false)
        initData()
        return binding.root
    }

    private fun initData() {

        val clickInterface = object : StoriesAdapter.ClickListener {
            override fun itemClick(position: Int) {

            }

        }

        val fakeList = mutableListOf<String>()
        fakeList.add("")
        fakeList.add("")
        fakeList.add("")
        fakeList.add("")
        fakeList.add("")
        fakeList.add("")
        fakeList.add("")

        val storiesAdapter = StoriesAdapter(fakeList, clickInterface)

        binding.apply {
            rvStories.layoutManager = GridLayoutManager(requireContext(), 2)
            rvStories.addItemDecoration(RecyclerItemDecoration(2, dpToPx(4f, resources)))
            rvStories.adapter = storiesAdapter
        }

    }

}
