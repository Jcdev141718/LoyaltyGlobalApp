package com.loyaltyglobal.ui.main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.loyaltyglobal.data.model.response.readNotification.ReadNotificationResponse
import com.loyaltyglobal.data.reposotory.HomeRepository
import com.loyaltyglobal.data.source.localModels.subBrandResponse.DealOffer
import com.loyaltyglobal.data.source.localModels.userPassResponse.CustomField
import com.loyaltyglobal.data.source.localModels.userPassResponse.Notification
import com.loyaltyglobal.data.source.localModels.userPassResponse.Pass
import com.loyaltyglobal.data.source.localModels.userPassResponse.Tier
import com.loyaltyglobal.data.source.network.NetworkResult
import com.loyaltyglobal.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
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
    var mPassData = MutableLiveData<Pass>()
    var mTierData = MutableLiveData<Tier>()
    var mReadNotificationResponse = MutableLiveData<NetworkResult<ReadNotificationResponse>>()
    var mainLoader: MutableLiveData<Boolean> = MutableLiveData(false)
    var swipeLoader: MutableLiveData<Boolean> = MutableLiveData(false)
    var currentStoriesData : MutableLiveData<Notification> = MutableLiveData()

    fun getStoriesList() {
        viewModelScope.launch {
            mStoriesList.postValue(homeRepository.getStoriesList())
        }
    }

    fun readNotification(notificationId: String) {
        viewModelScope.launch {
            mReadNotificationResponse.postValue(homeRepository.readNotification(notificationId))
        }
    }

    // get data from DB if available else api call
    fun getDashboardData() {
        viewModelScope.launch {
            if (homeRepository.isSubBrandsIsAvailableInDB()) {
                getAllDataFromDB()
            } else {
                dataFromApiCall()
            }
        }
    }

    fun dataFromApiCall(isRefresh: Boolean = false) {
        viewModelScope.launch {
            mainLoader.postValue(!isRefresh)
            awaitAll(async {
                homeRepository.getSubBrand()
            }, async {
                homeRepository.getUserPassFromAgency()
            })
            delay(1000)
            if (isRefresh) {
                swipeLoader.postValue(false)
            } else {
                mainLoader.postValue(false)
            }
            getAllDataFromDB()
        }
    }

    private suspend fun getAllDataFromDB() {
        mOfferList.postValue(homeRepository.getDealAndOffersList())
        mDealPromotionsList.postValue(homeRepository.getCustomFieldList())
        mStoriesList.postValue(homeRepository.getStoriesList())
        mPassData.postValue(homeRepository.getPassData())
        mTierData.postValue(homeRepository.getTiersData())
    }
}
