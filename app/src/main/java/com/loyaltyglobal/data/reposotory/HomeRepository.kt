package com.loyaltyglobal.data.reposotory

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.loyaltyglobal.data.source.local.DatabaseDAO
import com.loyaltyglobal.data.source.localModels.DollarPointModel
import com.loyaltyglobal.data.source.localModels.subBrandResponse.DealOffer
import com.loyaltyglobal.data.source.localModels.subBrandResponse.SubBrand
import com.loyaltyglobal.data.source.localModels.userPassResponse.CustomField
import com.loyaltyglobal.data.source.localModels.userPassResponse.Notification
import com.loyaltyglobal.data.source.localModels.userPassResponse.UserPassResponse
import com.loyaltyglobal.data.source.network.ApiService
import com.loyaltyglobal.data.source.network.BaseApiResponse
import com.loyaltyglobal.util.Constants.AGENCY_ID
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class HomeRepository @Inject constructor(
    private val apiService: ApiService, private val dataBaseDao: DatabaseDAO, @ApplicationContext context: Context,
) : BaseApiResponse(context) {

    suspend fun getSubBrand() {
        val response = safeApiCall { apiService.getSubBrand(AGENCY_ID) }
        response.responseData?.let { data ->
            data.coalition?.let { dataBaseDao.insertCoalition(it) }
            data.subBrands?.let { dataBaseDao.insertSubBrand(it) }
        }
    }

    suspend fun getUserPassFromAgency() {
        val response = safeApiCall { apiService.getUserPassFromAgency(AGENCY_ID) }
        response.responseData?.data?.let { data ->
            data.customFields?.let { dataBaseDao.insertCustomFields(it) }
            data.pass?.let { dataBaseDao.insertPass(it) }
            data.notification?.let { dataBaseDao.insertNotification(it) }
            data.tiers?.let { dataBaseDao.insertTiers(it) }
            data.perDollarPoint?.let { dataBaseDao.insertDollarPoint(DollarPointModel(perDollarPoint = it)) }
        }
    }

    suspend fun getDealAndOffersList() : ArrayList<DealOffer> {
        return ArrayList(dataBaseDao.getDealsAndOffers())
    }

//    suspend fun getStoriesList() : ArrayList<Notification> {
//        return ArrayList(dataBaseDao.getStoriesList())
//    }

    suspend fun getCustomFieldList() : ArrayList<CustomField> {
        return ArrayList(dataBaseDao.getCustomFieldList())
    }
}
