package com.loyaltyglobal.ui.main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.loyaltyglobal.data.reposotory.HomeRepository
import com.loyaltyglobal.data.source.localModels.subBrandResponse.DealOffer
import com.loyaltyglobal.data.source.localModels.userPassResponse.CustomField
import com.loyaltyglobal.data.source.localModels.userPassResponse.Notification
import com.loyaltyglobal.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Abhin.
 */

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository,
) : BaseViewModel() {

    var mOfferList = MutableLiveData<ArrayList<DealOffer>>()
    var mDealPromotionsList = MutableLiveData<ArrayList<CustomField>>()
    var mStoriesList = MutableLiveData<ArrayList<Notification?>>()

    fun getUserPassFromAgency() {
        viewModelScope.launch(coroutineScope) {
            homeRepository.getUserPassFromAgency()
        }
    }

    fun getSubBrands() {
        viewModelScope.launch(coroutineScope) {
            homeRepository.getSubBrand()
        }
    }

    fun getDealAndOffersList() {
        viewModelScope.launch(Dispatchers.IO) {
            mOfferList.postValue(homeRepository.getDealAndOffersList())
        }
    }

    fun getCustomFieldList() {
        viewModelScope.launch(Dispatchers.IO) {
            mDealPromotionsList.postValue(homeRepository.getCustomFieldList())
        }
    }

    fun getStoriesList() {
        viewModelScope.launch(Dispatchers.IO) {
            mStoriesList.postValue(homeRepository.getStoriesList())
        }
    }
}
