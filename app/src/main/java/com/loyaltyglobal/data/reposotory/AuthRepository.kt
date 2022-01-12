package com.loyaltyglobal.data.reposotory

import android.content.Context
import com.loyaltyglobal.data.model.request.LoginRequest
import com.loyaltyglobal.data.model.request.UpdateUserRequest
import com.loyaltyglobal.data.model.response.LoginResponse
import com.loyaltyglobal.data.model.response.updateUser.UpdateUserResponse
import com.loyaltyglobal.data.source.network.ApiService
import com.loyaltyglobal.data.source.network.BaseApiResponse
import com.loyaltyglobal.data.source.network.NetworkResult
import com.loyaltyglobal.util.Constants
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject


@ActivityRetainedScoped
class AuthRepository @Inject constructor(
    private val apiService: ApiService, @ApplicationContext context: Context) : BaseApiResponse(context) {

    suspend fun getLogin(loginRequest: LoginRequest) : NetworkResult<LoginResponse> = safeApiCall { apiService.login(loginRequest) }

    suspend fun enableNotification(oneSignalId: String) : NetworkResult<UpdateUserResponse> = safeApiCall { apiService.updateUserApi(
        UpdateUserRequest(oneSignalId = oneSignalId, agencyId = Constants.AGENCY_ID, platform = Constants.AGENCY_ID)) }

    suspend fun updateName(name : String) : NetworkResult<UpdateUserResponse> = safeApiCall { apiService.updateUserApi(
        UpdateUserRequest(firstName = name , lastName = " ")) }
}
