package com.loyaltyglobal.ui.main.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.loyaltyglobal.R
import com.loyaltyglobal.databinding.FragmentStoriesBinding

class StoriesFragment : Fragment() {

    lateinit var binding: FragmentStoriesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentStoriesBinding.inflate(inflater, container, false)
        initData()
        return binding.root
    }

    private fun initData() {

        binding.apply {


        }

    }

}
