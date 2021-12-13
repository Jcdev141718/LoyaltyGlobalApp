package com.loyaltyglobal.data.reposotory

import android.content.Context
import com.loyaltyglobal.data.model.request.LoginRequest
import com.loyaltyglobal.data.model.response.LoginResponse
import com.loyaltyglobal.data.source.network.ApiService
import com.loyaltyglobal.data.source.network.BaseApiResponse
import com.loyaltyglobal.data.source.network.NetworkResult
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped
import okhttp3.ResponseBody
import javax.inject.Inject


@ActivityRetainedScoped
class AuthRepository @Inject constructor(
    private val apiService: ApiService, @ApplicationContext context: Context) : BaseApiResponse(context) {

    suspend fun getLogin(loginRequest: LoginRequest) : NetworkResult<LoginResponse> = safeApiCall { apiService.login(loginRequest) }
}
