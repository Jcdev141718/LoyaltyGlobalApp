package com.loyaltyglobal.ui.main.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.loyaltyglobal.data.model.AllDaysModel
import com.loyaltyglobal.data.model.DealsAndOffersData
import com.loyaltyglobal.data.model.ExploreFilterData
import com.loyaltyglobal.data.reposotory.ExploreRepository
import com.loyaltyglobal.data.source.localModels.subBrandResponse.SubBrand
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

/**
 * Created by Abhin.
 */
@HiltViewModel
class ExploreViewModel @Inject constructor(
    private val exploreRepository: ExploreRepository
) : ViewModel() {

    private val days = arrayListOf("Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday")
    var mutableBusinessList = MutableLiveData<ArrayList<SubBrand>>()
    var mutableFilterList = MutableLiveData<ArrayList<ExploreFilterData>>()
    var mutableDealsAndOffersList = MutableLiveData<ArrayList<DealsAndOffersData>>()

    fun getBusinessList() {
        viewModelScope.launch {
            mutableBusinessList.postValue(exploreRepository.getAllSubBrandList())
        }
    }

    fun getDayList(offPercentage : String) : ArrayList<AllDaysModel> {
        val list = ArrayList<AllDaysModel>()
        days.forEach { day ->
            list.add(AllDaysModel(day,offPercentage))
        }
        val currentDay = Calendar.getInstance().getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault())
        val index = list.indexOfFirst { it.dayName == currentDay }
        Collections.rotate(list,-index)
        return list
    }

    fun getDealsAndOffersList() {
        val list = ArrayList<DealsAndOffersData>()
        list.add(DealsAndOffersData(brandName = "Brand name", discount = "Get 10% entire menu"))
        list.add(DealsAndOffersData(brandName = "Brand name", discount = "Get 10% entire menu"))
        list.add(DealsAndOffersData(brandName = "Brand name", discount = "Get 10% entire menu"))
        list.add(DealsAndOffersData(brandName = "Brand name", discount = "Get 10% entire menu"))
        list.add(DealsAndOffersData(brandName = "Brand name", discount = "Get 10% entire menu"))
        list.add(DealsAndOffersData(brandName = "Brand name", discount = "Get 10% entire menu"))
        list.add(DealsAndOffersData(brandName = "Brand name", discount = "Get 10% entire menu"))
        list.add(DealsAndOffersData(brandName = "Brand name", discount = "Get 10% entire menu"))
        mutableDealsAndOffersList.value = list
    }

    fun getFilterList() {
        val list = ArrayList<ExploreFilterData>()
        list.add(ExploreFilterData(filter_name = "Gift Cards & Mobile Recharges"))
        list.add(ExploreFilterData(filter_name = "Beauty, Health, Grocery"))
        list.add(ExploreFilterData(filter_name = "Mobiles, Computers"))
        list.add(ExploreFilterData(filter_name = "Books"))
        list.add(ExploreFilterData(filter_name = "Movies, Music & Video Games"))
        list.add(ExploreFilterData(filter_name = "Sports, Fitness, Bags, Luggage"))
        list.add(ExploreFilterData(filter_name = "Home, Kitchen, Pets"))
        list.add(ExploreFilterData(filter_name = "Toys, Baby Products, Kids Fashion"))
        list.add(ExploreFilterData(filter_name = "Women's Fashion"))
        list.add(ExploreFilterData(filter_name = "Men's Fashion"))
        list.add(ExploreFilterData(filter_name = "Car, Motorbike, Industrial"))
        list.add(ExploreFilterData(filter_name = "Home & Garden / Major Appliance / Freezers"))
        mutableFilterList.value = list
    }
}
