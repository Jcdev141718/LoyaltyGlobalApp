package com.loyaltyglobal.ui.main.viewmodel

import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.loyaltyglobal.data.model.AllDaysModel
import com.loyaltyglobal.data.model.DealsAndOffersData
import com.loyaltyglobal.data.model.ExploreFilterData
import com.loyaltyglobal.data.reposotory.ExploreRepository
import com.loyaltyglobal.data.source.localModels.SubBrandAndCoalition
import com.loyaltyglobal.data.source.localModels.subBrandResponse.DealOffer
import com.loyaltyglobal.data.source.localModels.subBrandResponse.SubBrand
import com.loyaltyglobal.util.dialPhoneNum
import com.loyaltyglobal.util.sendEmailTo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

/**
 * Created by Abhin.
 */
@HiltViewModel
class ExploreViewModel @Inject constructor(
    private val exploreRepository: ExploreRepository,
) : ViewModel() {

    private val days = arrayListOf("Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday")
    var mutableBusinessList = MutableLiveData<ArrayList<SubBrandAndCoalition>>()
    var mutableFilterList = MutableLiveData<ArrayList<String>>()
    var mutableDealsAndOffersList = MutableLiveData<ArrayList<DealOffer>>()

    var brandDetailsData = MutableLiveData<SubBrandAndCoalition>()

    fun getBusinessList() {
        viewModelScope.launch {
            mutableBusinessList.postValue(exploreRepository.getSubBrandWithCoalitionData())
        }
    }

    fun getDealAndOffersList(){
        viewModelScope.launch {
            mutableDealsAndOffersList.postValue(exploreRepository.getDealAndOffersList())
        }
    }

    fun getDayList() : ArrayList<AllDaysModel> {
        val list = ArrayList<AllDaysModel>()
        val discountList = ArrayList<Int>()
        brandDetailsData.value?.let { data ->
            discountList.addAll(data.coalition.tiers?.benefits?.discount?.customDiscounts as ArrayList)
            days.forEachIndexed { index, day ->
                list.add(AllDaysModel(day, discountList[index].toString()))
            }
            val currentDay = Calendar.getInstance().getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault())
            val index = list.indexOfFirst { it.dayName == currentDay }
            Collections.rotate(list,-index)
        }
        return list
    }

    fun getFilterList(){
        viewModelScope.launch {
            mutableFilterList.postValue(exploreRepository.getFilters())
        }
    }

    fun onPhoneNumClick(view : View,phone : String){
        view.context.dialPhoneNum(phone)
    }

    fun onEmailClick(view : View){
        view.context.sendEmailTo(brandDetailsData.value?.subBrand?.email.toString())
    }
}
