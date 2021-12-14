package com.loyaltyglobal.data.reposotory

import android.content.Context
import com.loyaltyglobal.data.source.network.ApiService
import com.loyaltyglobal.data.source.localModels.subBrandResponse.SubBrandResponse
import com.loyaltyglobal.data.source.localModels.userPassResponse.UserPassResponse
import com.loyaltyglobal.data.source.network.ApiService
import com.loyaltyglobal.data.source.network.BaseApiResponse
import com.loyaltyglobal.data.source.network.NetworkResult
import com.loyaltyglobal.util.Constants.AGENCY_ID
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class HomeRepository @Inject constructor(
    private val apiService: ApiService, @ApplicationContext context: Context) : BaseApiResponse(context) {

    suspend fun getSubBrand() : NetworkResult<SubBrandResponse> {
        return safeApiCall { apiService.getSubBrand(AGENCY_ID) }
    }

    suspend fun getUserPassFromAgency() : NetworkResult<UserPassResponse> {
        return safeApiCall { apiService.getUserPassFromAgency(AGENCY_ID) }
    }
}
