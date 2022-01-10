package com.loyaltyglobal.ui.main.viewmodel

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.loyaltyglobal.data.model.AllDaysModel
import com.loyaltyglobal.data.model.response.transactionData.Data
import com.loyaltyglobal.data.model.response.transactionData.TransactionData
import com.loyaltyglobal.data.reposotory.ExploreRepository
import com.loyaltyglobal.data.reposotory.HomeRepository
import com.loyaltyglobal.data.source.localModels.FilterModel
import com.loyaltyglobal.data.source.localModels.LinkKeyValueModel
import com.loyaltyglobal.data.source.localModels.SubBrandAndCoalition
import com.loyaltyglobal.data.source.localModels.subBrandResponse.DealOffer
import com.loyaltyglobal.data.source.network.NetworkResult
import com.loyaltyglobal.util.dialPhoneNum
import com.loyaltyglobal.util.sendEmailTo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

/**
 * Created by Abhin.
 */
@HiltViewModel
class ExploreViewModel @Inject constructor(
    private val exploreRepository: ExploreRepository,
    private val homeRepository: HomeRepository,
) : ViewModel() {

    private val days = arrayListOf("Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday")
    var mutableBusinessList = MutableLiveData<ArrayList<SubBrandAndCoalition>>()
    var mutableFilterList = MutableLiveData<ArrayList<FilterModel>>()
    var mutableDealsAndOffersList = MutableLiveData<ArrayList<DealOffer>>()
    var mutableUrlLinks = MutableLiveData<ArrayList<LinkKeyValueModel>>()
    var transactionData = MutableLiveData<NetworkResult<TransactionData>>()

    var transactionList = MutableLiveData<List<Data?>?>()

    var brandId: String? = null
    var mutableFiltersList = MutableLiveData<ArrayList<String>>()
    var brandDetailsData = MutableLiveData<SubBrandAndCoalition>()

    fun getBusinessList() {
        viewModelScope.launch {
            mutableBusinessList.postValue(exploreRepository.getSubBrandWithCoalitionData())
        }
    }

    fun getBrandDetails() {
        viewModelScope.launch {
            brandId?.let {
                brandDetailsData.postValue(exploreRepository.getSubBrandWithCoalitionDataById(it))
            }
        }
    }

    fun getDealAndOffersList() {
        viewModelScope.launch {
            mutableDealsAndOffersList.postValue(exploreRepository.getDealAndOffersList())
        }
    }

    fun getDayList(): ArrayList<AllDaysModel> {
        val list = ArrayList<AllDaysModel>()
        val discountList = ArrayList<Int>()
        brandDetailsData.value?.let { data ->
            discountList.addAll(data.coalition.tiers?.benefits?.discount?.customDiscounts as ArrayList)
            days.forEachIndexed { index, day ->
                list.add(AllDaysModel(day, discountList[index].toString()))
            }
            val currentDay = Calendar.getInstance().getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault())
            val index = list.indexOfFirst { it.dayName == currentDay }
            Collections.rotate(list, -index)
        }
        return list
    }

    fun getFilterList() {
        viewModelScope.launch {
            val filtersList = exploreRepository.getFilters()
            val filtersModelList = ArrayList<FilterModel>()
            filtersList.map {
                filtersModelList.add(FilterModel(it))
            }
            mutableFilterList.postValue(filtersModelList)
        }
    }

    fun onPhoneNumClick(view: View, phone: String) {
        view.context.dialPhoneNum(phone)
    }

    fun onEmailClick(view: View) {
        view.context.sendEmailTo(brandDetailsData.value?.subBrand?.email.toString())
    }

    fun getLinksData() {
        brandDetailsData.value?.let { data ->
            viewModelScope.launch {
                mutableUrlLinks.postValue(exploreRepository.getKeyValueData(data.subBrand._id))
            }
        }
    }

    fun setFilters(filters: List<String>, isReset: Boolean = false) {
        mutableFiltersList.postValue(ArrayList(filters))
    }


    fun getTransactionData() {
        transactionData.value = NetworkResult.Loading()
        viewModelScope.launch {
            val allTransaction = homeRepository.getTransaction()
            val subBrandMap = homeRepository.getSubBrandMap()
            val result = withContext(Dispatchers.IO) {
                allTransaction.responseData?.data?.filter { subBrandMap[it.brandId] != null }?.map { transection ->
                    subBrandMap[transection.brandId]?.let { subBrand ->
                        transection.apply {
                            brandName = subBrand.brandName
                            brandLogo = subBrand.brandLogo
                        }
                    }
                }
            }
            transactionData.postValue(allTransaction)
            transactionList.postValue(result)

        }
    }
}
