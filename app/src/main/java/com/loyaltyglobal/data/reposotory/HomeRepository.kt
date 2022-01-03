package com.loyaltyglobal.data.reposotory

import android.content.Context
import com.loyaltyglobal.data.model.request.ReadNotificationRequest
import com.loyaltyglobal.data.model.response.readNotification.ReadNotificationResponse
import com.loyaltyglobal.data.model.response.readNotification.toNotificationOBJ
import com.loyaltyglobal.data.source.local.DatabaseDAO
import com.loyaltyglobal.data.source.localModels.DollarPointModel
import com.loyaltyglobal.data.source.localModels.subBrandResponse.DealOffer
import com.loyaltyglobal.data.source.localModels.subBrandResponse.SubBrand
import com.loyaltyglobal.data.source.localModels.userPassResponse.CustomField
import com.loyaltyglobal.data.source.localModels.userPassResponse.Notification
import com.loyaltyglobal.data.source.localModels.userPassResponse.Pass
import com.loyaltyglobal.data.source.localModels.userPassResponse.Tier
import com.loyaltyglobal.data.source.network.ApiService
import com.loyaltyglobal.data.source.network.BaseApiResponse
import com.loyaltyglobal.data.source.network.NetworkResult
import com.loyaltyglobal.util.Constants
import com.loyaltyglobal.util.Constants.AGENCY_ID
import com.loyaltyglobal.util.PreferenceProvider
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@ActivityRetainedScoped
class HomeRepository @Inject constructor(
    private val apiService: ApiService,
    private val dataBaseDao: DatabaseDAO,
    @ApplicationContext context: Context,
    private val preferenceProvider: PreferenceProvider,
) : BaseApiResponse(context) {

    suspend fun getSubBrand() {
        preferenceProvider.setValue(Constants.KEY_LAST_REFRESH_TIMESTAMP, System.currentTimeMillis())
        val response = safeApiCall { apiService.getSubBrand(AGENCY_ID) }
        response.responseData?.let { data ->
            data.coalition?.let { dataBaseDao.insertCoalition(it) }
            data.subBrands?.let {
                dataBaseDao.insertSubBrand(it)
            }
        }
    }

    suspend fun getUserPassFromAgency() {
        val response = safeApiCall { apiService.getUserPassFromAgency(AGENCY_ID) }
        response.responseData?.data?.let { data ->
            data.customFields?.let { dataBaseDao.insertCustomFields(it) }
            data.pass?.let { dataBaseDao.insertPass(it) }
            data.notification?.map { it.userId = preferenceProvider.getUserId() }
            data.notification?.let { dataBaseDao.insertNotification(it) }
            data.tiers?.let { dataBaseDao.insertTiers(it) }
            data.perDollarPoint?.let { dataBaseDao.insertDollarPoint(DollarPointModel(perDollarPoint = it)) }
        }
    }

    suspend fun getDealAndOffersList(): ArrayList<DealOffer> {
        return ArrayList(dataBaseDao.getDealsAndOffers().filter { !it.image.isNullOrEmpty() })
    }

    suspend fun getStoriesList(): ArrayList<Notification?> {
        val subBrandList = withContext(Dispatchers.IO) { dataBaseDao.getSubBrandNameAndLogo() }
        val storiesList = ArrayList(dataBaseDao.getStoriesList())
        val subBrandMap: Map<String, SubBrand> = subBrandList.associateBy { it._id }
        val result = withContext(Dispatchers.IO) {
            storiesList.filter { subBrandMap[it.brandId] != null && subBrandMap[it.brandId]?.delete == false }
                .filter { it.sendTo?.contains(preferenceProvider.getUserId()) == true }.map { notification ->
                    subBrandMap[notification.brandId]?.let { subBrand ->
                        notification?.apply {
                            branName = subBrand.brandName
                            brandLogo = subBrand.brandLogo
                            userId = preferenceProvider.getUserId()
                        }
                    }
                }
        }
        return ArrayList(result)
    }

    suspend fun getCustomFieldList(): ArrayList<CustomField> {
        return ArrayList(dataBaseDao.getCustomFieldList())
    }

    suspend fun isSubBrandsIsAvailableInDB(): Boolean = dataBaseDao.countOfSubBrands() != 0 && dataBaseDao.countOfPassData() != 0

    suspend fun getPassData(): Pass? = preferenceProvider.getUserId()?.let { dataBaseDao.getPassData(it) }

    suspend fun getTiersData(): Tier? = preferenceProvider.getUserId()?.let { dataBaseDao.getPointsDataFromTiers(it) }

    suspend fun readNotification(notificationId: String): NetworkResult<ReadNotificationResponse> {
        val response = safeApiCall { apiService.readNotification(ReadNotificationRequest(notificationId)) }
        if (response.statusCode == 200) {
            response.responseData?.data?.toNotificationOBJ(isOpenedOnce = true)?.let { dataBaseDao.updateNotification(it) }
        }
        return response
    }
}
