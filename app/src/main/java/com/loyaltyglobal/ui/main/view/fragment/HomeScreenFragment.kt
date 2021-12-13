package com.loyaltyglobal.ui.main.view.fragment

/**
 * Created by Abhin.
 */
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.loyaltyglobal.R
import com.loyaltyglobal.data.model.response.HomeScreenDealPromotionsData
import com.loyaltyglobal.data.model.response.HomeScreenStoriesData
import com.loyaltyglobal.data.model.response.MyDealOfferData
import com.loyaltyglobal.databinding.FragmentHomeScreenBinding
import com.loyaltyglobal.ui.base.BaseFragment
import com.loyaltyglobal.ui.main.adapter.HomeScreenDealPromotionsAdapter
import com.loyaltyglobal.ui.main.adapter.HomeScreenStoriesAdapter
import com.loyaltyglobal.ui.main.adapter.MyDealOfferAdapter
import com.loyaltyglobal.ui.main.viewmodel.HomeViewModel
import com.loyaltyglobal.util.addReplaceFragment
import com.loyaltyglobal.util.clickWithDebounce
import com.loyaltyglobal.util.setImage
import dagger.hilt.android.AndroidEntryPoint

@SuppressLint("NotifyDataSetChanged")
@AndroidEntryPoint
class HomeScreenFragment : BaseFragment() {

    private lateinit var binding: FragmentHomeScreenBinding

    private var mMyDealOfferAdapter: MyDealOfferAdapter? = null
    private var mMyDealOfferList = ArrayList<MyDealOfferData>()

    private var mHomeScreenStoriesAdapter: HomeScreenStoriesAdapter? = null
    private var mHomeScreenStoriesDataList = ArrayList<HomeScreenStoriesData>()

    private var mHomeScreenDealPromotionsAdapter: HomeScreenDealPromotionsAdapter? = null
    private var mHomeScreenDealPromotionsList = ArrayList<HomeScreenDealPromotionsData>()

    private val homeViewModel: HomeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home_screen, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initObserver()
    }

    private fun initObserver() {
        homeViewModel.mOfferList.observe(this, {
            if (!it.isNullOrEmpty()) {
                mMyDealOfferList.clear()
                mMyDealOfferList.addAll(it)
                mMyDealOfferAdapter?.notifyDataSetChanged()
            }
        })

        homeViewModel.mStoriesList.observe(this, {
            if (!it.isNullOrEmpty()) {
                mHomeScreenStoriesDataList.clear()
                mHomeScreenStoriesDataList.addAll(it)
                mHomeScreenStoriesAdapter?.notifyDataSetChanged()
            }
        })

        homeViewModel.mDealPromotionsList.observe(this, {
            if (!it.isNullOrEmpty()) {
                mHomeScreenDealPromotionsList.clear()
                mHomeScreenDealPromotionsList.addAll(it)
                mHomeScreenDealPromotionsAdapter?.notifyDataSetChanged()
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        binding.layoutHomeScreenToolbar.imgLogo.setImage(R.drawable.icon_logo)
        setClick()
    }

    private fun setClick() {
        binding.layoutHomeScreenToolbar.imgNotification.clickWithDebounce {}
        binding.layoutHomeScreenToolbar.imgCamera.clickWithDebounce {}
        binding.layoutHomeScreenToolbar.imgQrCode.clickWithDebounce {}
        binding.layoutHomeScreenMyDealOffer.txtSeeAll.clickWithDebounce {}
        binding.layoutHomeScreenStories.txtSeeAll.clickWithDebounce {
            activity?.addReplaceFragment(
                R.id.fl_container, StoriesFragment(), addFragment = true, addToBackStack = true
            )
        }

        initMyDealOfferRecyclerView()
        initHomeScreenStoriesRecyclerView()
        initFeatureDealRecyclerView()
        homeViewModel.dummyOfferList()
        homeViewModel.dummyStories()
        homeViewModel.dummyFeatureDeal()
    }

    private fun initMyDealOfferRecyclerView() {
        val layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.layoutHomeScreenMyDealOffer.rvDealOffer.layoutManager = layoutManager
        mMyDealOfferAdapter = MyDealOfferAdapter(
            requireContext(),
            mMyDealOfferList,
            object : MyDealOfferAdapter.ItemClickListener {
                override fun itemClick(position: Int) {

                }
            })
        binding.layoutHomeScreenMyDealOffer.rvDealOffer.adapter = mMyDealOfferAdapter
    }

    private fun initHomeScreenStoriesRecyclerView() {
        val layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.layoutHomeScreenStories.rvStories.layoutManager = layoutManager
        mHomeScreenStoriesAdapter = HomeScreenStoriesAdapter(requireContext(),
            mHomeScreenStoriesDataList,
            object : HomeScreenStoriesAdapter.ItemClickListener {
                override fun itemClick(position: Int) {

                }
            })
        binding.layoutHomeScreenStories.rvStories.adapter = mHomeScreenStoriesAdapter
    }

    private fun initFeatureDealRecyclerView() {
        val layoutManager = LinearLayoutManager(requireContext())
        binding.layoutHomeScreenFeatureDealsPromotions.rvDealOffer.layoutManager = layoutManager
        mHomeScreenDealPromotionsAdapter = HomeScreenDealPromotionsAdapter(
            mHomeScreenDealPromotionsList,
            object : HomeScreenDealPromotionsAdapter.ItemClickListener {
                override fun itemClick(position: Int) {

                }
            })
        binding.layoutHomeScreenFeatureDealsPromotions.rvDealOffer.adapter =
            mHomeScreenDealPromotionsAdapter
    }
}
