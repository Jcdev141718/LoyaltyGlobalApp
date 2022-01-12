package com.loyaltyglobal.ui.main.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.maps.model.LatLng
import com.loyaltyglobal.R
import com.loyaltyglobal.data.source.localModels.SubBrandAndCoalition
import com.loyaltyglobal.databinding.FragmentSearchBrandsBinding
import com.loyaltyglobal.ui.main.adapter.SubBrandAdapter
import com.loyaltyglobal.ui.main.view.activity.MainActivity
import com.loyaltyglobal.ui.main.viewmodel.ExploreViewModel
import com.loyaltyglobal.util.*

/**
 * Created by Abhin.
 */
class SearchBrandsFragment : Fragment() {

    private lateinit var binding: FragmentSearchBrandsBinding
    private val exploreViewModel: ExploreViewModel by activityViewModels()
    private var brandAdapter: SubBrandAdapter? = null
    private var brandList: ArrayList<SubBrandAndCoalition> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initObserver()
    }

    private fun initObserver() {
        exploreViewModel.mutableBusinessList.observe(this, {
            if (!it.isNullOrEmpty()) {
                brandList.clear()
                brandList.addAll(it)
            }
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentSearchBrandsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
    }

    private fun initRecyclerView() {
        val latitude = (activity as MainActivity).gpsTracker?.getLatitude()
        val longitude = (activity as MainActivity).gpsTracker?.getLongitude()
        val latLong = latitude?.let { lat ->
            longitude?.let { long ->
                LatLng(lat, long)
            }

        }
        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvBrands.layoutManager = layoutManager
        brandAdapter = SubBrandAdapter(latLong, object : SubBrandAdapter.SubBrandItemClickListener {
            override fun clickListener(position: Int) {
                exploreViewModel.brandId = brandAdapter?.currentList?.get(position)?.subBrand?._id
                activity?.addReplaceFragment(R.id.fl_main_container, ExploreDetailsFragment(), true, true)
            }
        })
        binding.rvBrands.adapter = brandAdapter

        binding.edtSearch.doOnTextChanged { text, _, _, _ ->
            if (!text.isNullOrEmpty()) {
                val filteredList = brandList.filter {
                    it.subBrand.brandName?.contains(text) == true || it.subBrand.locationType?.contains(text) == true
                }
                brandAdapter?.submitList(filteredList)

                showNoSearchedItemUI(filteredList.isEmpty())
            } else {
                brandAdapter?.submitList(brandList)
            }
        }

        binding.imgBack.clickWithDebounce { activity?.popFragment() }
    }

    private fun showNoSearchedItemUI(isEmptyList: Boolean) {
        if (isEmptyList) {
            binding.rvBrands.hide()
            binding.txtNoItemFound.show()
        } else {
            binding.rvBrands.show()
            binding.txtNoItemFound.hide()
        }
    }

    override fun onDestroyView() {
        activity?.hideKeyboard()
        (activity as MainActivity).showHideBottomNavigationBar(isShow = true)
        super.onDestroyView()
    }
}