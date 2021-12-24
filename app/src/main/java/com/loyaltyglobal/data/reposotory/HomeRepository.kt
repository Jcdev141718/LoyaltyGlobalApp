package com.loyaltyglobal.data.reposotory

import android.content.Context
import com.loyaltyglobal.data.source.local.DatabaseDAO
import com.loyaltyglobal.data.source.localModels.DollarPointModel
import com.loyaltyglobal.data.source.localModels.subBrandResponse.DealOffer
import com.loyaltyglobal.data.source.localModels.subBrandResponse.SubBrand
import com.loyaltyglobal.data.source.localModels.userPassResponse.CustomField
import com.loyaltyglobal.data.source.localModels.userPassResponse.Notification
import com.loyaltyglobal.data.source.network.ApiService
import com.loyaltyglobal.data.source.network.BaseApiResponse
import com.loyaltyglobal.util.Constants.AGENCY_ID
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@ActivityRetainedScoped
class HomeRepository @Inject constructor(
    private val apiService: ApiService, private val dataBaseDao: DatabaseDAO, @ApplicationContext context: Context,
) : BaseApiResponse(context) {

    suspend fun getSubBrand() {
        if (dataBaseDao.countOfSubBrands() == 0) {
            val response = safeApiCall { apiService.getSubBrand(AGENCY_ID) }
            response.responseData?.let { data ->
                data.coalition?.let { dataBaseDao.insertCoalition(it) }
                data.subBrands?.let { dataBaseDao.insertSubBrand(it) }
            }
        }
    }

    suspend fun getUserPassFromAgency() {
        if (dataBaseDao.countOfCustomFields() == 0) {
            val response = safeApiCall { apiService.getUserPassFromAgency(AGENCY_ID) }
            response.responseData?.data?.let { data ->
                data.customFields?.let { dataBaseDao.insertCustomFields(it) }
                data.pass?.let { dataBaseDao.insertPass(it) }
                data.notification?.let { dataBaseDao.insertNotification(it) }
                data.tiers?.let { dataBaseDao.insertTiers(it) }
                data.perDollarPoint?.let { dataBaseDao.insertDollarPoint(DollarPointModel(perDollarPoint = it)) }
            }
        }
    }

    suspend fun getDealAndOffersList() : ArrayList<DealOffer> {
        return ArrayList(dataBaseDao.getDealsAndOffers())
    }

    suspend fun getStoriesList() : ArrayList<Notification?> {
        val subBrandList = withContext(Dispatchers.IO) { dataBaseDao.getSubBrandNameAndLogo() }
        val storiesList = ArrayList(dataBaseDao.getStoriesList())
        val subBrandMap : Map<String, SubBrand> = subBrandList.associateBy { it._id }
        val result = storiesList.filter { subBrandMap[it.brandId] != null }.map { notification ->
            subBrandMap[notification.brandId]?.let { subBrand ->
                notification?.apply {
                    branName = subBrand.brandName
                    brandLogo = subBrand.brandLogo
                }
            }
        }
        return ArrayList(result)
    }

    suspend fun getCustomFieldList() : ArrayList<CustomField> {
        return ArrayList(dataBaseDao.getCustomFieldList())
    }

    suspend fun isDataIsAvailableInDB() : Boolean = dataBaseDao.countOfSubBrands() != 0

    suspend fun updateStoryItemIntoDB(storyId : String) = dataBaseDao.updateStoryItem(storyId)
}
