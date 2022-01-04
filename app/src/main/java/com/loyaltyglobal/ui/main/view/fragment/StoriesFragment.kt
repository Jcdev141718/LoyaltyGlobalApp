package com.loyaltyglobal.ui.main.view.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.loyaltyglobal.data.source.localModels.userPassResponse.Notification
import com.loyaltyglobal.databinding.FragmentStoriesBinding
import com.loyaltyglobal.ui.main.adapter.StoriesAdapter
import com.loyaltyglobal.ui.main.view.activity.FullscreenNotificationActivity
import com.loyaltyglobal.ui.main.viewmodel.HomeViewModel
import com.loyaltyglobal.util.*
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by Abhin.
 */

@AndroidEntryPoint
@SuppressLint("NotifyDataSetChanged")
class StoriesFragment : Fragment() {

    lateinit var binding: FragmentStoriesBinding
    private var storiesAdapter: StoriesAdapter? = null
    private var mStoriesList = ArrayList<Notification?>()
    private val homeViewModel: HomeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?,
    ): View {
        binding = FragmentStoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initObserver()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        homeViewModel.getStoriesList()
    }

    private fun initObserver() {
        homeViewModel.mStoriesList.observe(this, {
            if (!it.isNullOrEmpty()) {
                mStoriesList.clear()
                it.map { notification ->
                    notification?.isOpenedOnce = notification?.readBy?.contains(notification.userId) == true
                }
                mStoriesList.addAll(it.sortedBy { it?.isOpenedOnce })
                storiesAdapter?.notifyDataSetChanged()
                setEmptyViewForStories(false)
            }else {
                setEmptyViewForStories(true)
            }
        })
    }

    private fun initData() {
        storiesAdapter = StoriesAdapter(mStoriesList, object : StoriesAdapter.ClickListener {
            override fun itemClick(position: Int) {
                mStoriesList[position]?.let {
                    if (!it.isOpenedOnce) {
                        homeViewModel.readNotification(it._id)
                        it.isOpenedOnce = true
                        storiesAdapter?.notifyItemChanged(position)
                    }
                    mStoriesList.sortedBy { it?.isOpenedOnce }
                    storiesAdapter?.notifyDataSetChanged()
                    startActivity(Intent(activity,FullscreenNotificationActivity::class.java).apply {
                        putExtra(Constants.NOTIFICATION_KEY,it)
                    })
                }
            }
        })

        binding.apply {
            rvStories.layoutManager = GridLayoutManager(requireContext(), 2)
            rvStories.addItemDecoration(RecyclerItemDecoration(2, dpToPx(4f, resources)))
            rvStories.adapter = storiesAdapter
        }
    }

    private fun setEmptyViewForStories(isEmpty: Boolean) = if (isEmpty) {
        binding.apply {
            llNoStories.root.show()
            rvStories.hide()
        }
    } else {
        binding.apply {
            llNoStories.root.hide()
            rvStories.show()
        }
    }

}
