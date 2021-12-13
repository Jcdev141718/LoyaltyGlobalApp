package com.loyaltyglobal.ui.main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.loyaltyglobal.data.model.response.HomeScreenDealPromotionsData
import com.loyaltyglobal.data.model.response.HomeScreenStoriesData
import com.loyaltyglobal.data.model.response.MyDealOfferData
import com.loyaltyglobal.data.reposotory.HomeRepository
import com.loyaltyglobal.data.source.local.DatabaseDAO
import com.loyaltyglobal.data.source.localModels.DollarPointModel
import com.loyaltyglobal.data.source.localModels.userPassResponse.UserPassResponse
import com.loyaltyglobal.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Abhin.
 */

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository,
    private val dataBaseDao: DatabaseDAO
) : BaseViewModel() {

    var mOfferList = MutableLiveData<ArrayList<MyDealOfferData>>()
    var offerList = ArrayList<MyDealOfferData>()
    var mDealPromotionsList = MutableLiveData<ArrayList<HomeScreenDealPromotionsData>>()
    var dealPromotionsList = ArrayList<HomeScreenDealPromotionsData>()
    var mStoriesList = MutableLiveData<ArrayList<HomeScreenStoriesData>>()
    var storiesList = ArrayList<HomeScreenStoriesData>()

    fun getUserPassFromAgency() {
        viewModelScope.launch(coroutineScope) {
            try {
                val response = homeRepository.getUserPassFromAgency()
                response.responseData?.data?.let { data ->
                    data.customFields?.let { dataBaseDao.insertCustomFields(it) }
                    data.pass?.let { dataBaseDao.insertPass(it) }
                    data.notification?.let { dataBaseDao.insertNotification(it) }
                    data.tiers?.let { dataBaseDao.insertTiers(it) }
                    data.perDollarPoint?.let { dataBaseDao.insertDollarPoint(DollarPointModel(perDollarPoint = it)) }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun getSubBrands() {
        viewModelScope.launch(coroutineScope) {
            try {
                val response = homeRepository.getSubBrand()
                response.responseData?.let { data ->
                    data.coalition?.let { dataBaseDao.insertCoalition(it) }
                    data.subBrands?.let { dataBaseDao.insertSubBrand(it) }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun dummyOfferList(){
        mOfferList.value?.clear()
        offerList.clear()
        offerList.add(MyDealOfferData("Brand Name 1","Get 10% entire menu", thumbnailUrl = "https://picsum.photos/200/300"))
        offerList.add(MyDealOfferData("Brand Name 2","Get 20% entire menu", thumbnailUrl = "https://picsum.photos/200/300"))
        offerList.add(MyDealOfferData("Brand Name 3","Get 30% entire menu", thumbnailUrl = "https://picsum.photos/200/300"))
        offerList.add(MyDealOfferData("Brand Name 4","Get 40% entire menu", thumbnailUrl = "https://picsum.photos/200/300"))
        offerList.add(MyDealOfferData("Brand Name 5","Get 50% entire menu", thumbnailUrl = "https://picsum.photos/200/300"))
        offerList.add(MyDealOfferData("Brand Name 6","Get 60% entire menu", thumbnailUrl = "https://picsum.photos/200/300"))
        offerList.add(MyDealOfferData("Brand Name 7","Get 70% entire menu", thumbnailUrl = "https://picsum.photos/200/300"))
        offerList.add(MyDealOfferData("Brand Name 8","Get 80% entire menu", thumbnailUrl = "https://picsum.photos/200/300"))
        offerList.add(MyDealOfferData("Brand Name 9","Get 90% entire menu", thumbnailUrl = "https://picsum.photos/200/300"))
        offerList.add(MyDealOfferData("Brand Name 10","Get 100% entire menu", thumbnailUrl = "https://picsum.photos/200/300"))
        mOfferList.postValue(offerList)
    }

    fun dummyStories(){
        mStoriesList.value?.clear()
        storiesList.clear()
        storiesList.add(HomeScreenStoriesData("Brand Name 1", description = "Get 10% entire menu", thumbnailUrl = "https://picsum.photos/200/300", brandImageUrl = "https://picsum.photos/200/300"))
        storiesList.add(HomeScreenStoriesData("Brand Name 2",description = "Get 20% entire menu", thumbnailUrl = "https://picsum.photos/200/300", brandImageUrl = "https://picsum.photos/200/300"))
        storiesList.add(HomeScreenStoriesData("Brand Name 3",description = "Get 30% entire menu", thumbnailUrl = "https://picsum.photos/200/300", brandImageUrl = "https://picsum.photos/200/300"))
        storiesList.add(HomeScreenStoriesData("Brand Name 4",description = "Get 40% entire menu", thumbnailUrl = "https://picsum.photos/200/300", brandImageUrl = "https://picsum.photos/200/300"))
        storiesList.add(HomeScreenStoriesData("Brand Name 5",description = "Get 50% entire menu", thumbnailUrl = "https://picsum.photos/200/300", brandImageUrl = "https://picsum.photos/200/300"))
        storiesList.add(HomeScreenStoriesData("Brand Name 6",description = "Get 60% entire menu", thumbnailUrl = "https://picsum.photos/200/300", brandImageUrl = "https://picsum.photos/200/300"))
        storiesList.add(HomeScreenStoriesData("Brand Name 7",description = "Get 70% entire menu", thumbnailUrl = "https://picsum.photos/200/300", brandImageUrl = "https://picsum.photos/200/300"))
        storiesList.add(HomeScreenStoriesData("Brand Name 8",description = "Get 80% entire menu", thumbnailUrl = "https://picsum.photos/200/300", brandImageUrl = "https://picsum.photos/200/300"))
        storiesList.add(HomeScreenStoriesData("Brand Name 9",description = "Get 90% entire menu", thumbnailUrl = "https://picsum.photos/200/300", brandImageUrl = "https://picsum.photos/200/300"))
        storiesList.add(HomeScreenStoriesData("Brand Name 10",description = "Get 100% entire menu", thumbnailUrl = "https://picsum.photos/200/300", brandImageUrl = "https://picsum.photos/200/300"))
        mStoriesList.postValue(storiesList)
    }

    fun dummyFeatureDeal(){
        mDealPromotionsList.value?.clear()
        dealPromotionsList.clear()
        dealPromotionsList.add(HomeScreenDealPromotionsData("Brand Name 1", description = "Get 10% entire menu", thumbnailUrl = "https://picsum.photos/200/300", brandImageUrl = "https://picsum.photos/200/300"))
        dealPromotionsList.add(HomeScreenDealPromotionsData("Brand Name 2",description = "Get 20% entire menu", thumbnailUrl = "https://picsum.photos/200/300", brandImageUrl = "https://picsum.photos/200/300"))
        dealPromotionsList.add(HomeScreenDealPromotionsData("Brand Name 3",description = "Get 30% entire menu", thumbnailUrl = "https://picsum.photos/200/300", brandImageUrl = "https://picsum.photos/200/300"))
        dealPromotionsList.add(HomeScreenDealPromotionsData("Brand Name 4",description = "Get 40% entire menu", thumbnailUrl = "https://picsum.photos/200/300", brandImageUrl = "https://picsum.photos/200/300"))
        dealPromotionsList.add(HomeScreenDealPromotionsData("Brand Name 5",description = "Get 50% entire menu", thumbnailUrl = "https://picsum.photos/200/300", brandImageUrl = "https://picsum.photos/200/300"))
        dealPromotionsList.add(HomeScreenDealPromotionsData("Brand Name 6",description = "Get 60% entire menu", thumbnailUrl = "https://picsum.photos/200/300", brandImageUrl = "https://picsum.photos/200/300"))
        dealPromotionsList.add(HomeScreenDealPromotionsData("Brand Name 7",description = "Get 70% entire menu", thumbnailUrl = "https://picsum.photos/200/300", brandImageUrl = "https://picsum.photos/200/300"))
        dealPromotionsList.add(HomeScreenDealPromotionsData("Brand Name 8",description = "Get 80% entire menu", thumbnailUrl = "https://picsum.photos/200/300", brandImageUrl = "https://picsum.photos/200/300"))
        dealPromotionsList.add(HomeScreenDealPromotionsData("Brand Name 9",description = "Get 90% entire menu", thumbnailUrl = "https://picsum.photos/200/300", brandImageUrl = "https://picsum.photos/200/300"))
        dealPromotionsList.add(HomeScreenDealPromotionsData("Brand Name 10",description = "Get 100% entire menu", thumbnailUrl = "https://picsum.photos/200/300", brandImageUrl = "https://picsum.photos/200/300"))
        mDealPromotionsList.postValue(dealPromotionsList)
    }
}
