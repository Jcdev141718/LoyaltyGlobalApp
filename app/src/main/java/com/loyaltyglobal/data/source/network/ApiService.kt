package com.loyaltyglobal.data.source.network

import com.loyaltyglobal.data.source.localModels.subBrandResponse.SubBrandResponse
import com.loyaltyglobal.data.source.localModels.userPassResponse.UserPassResponse
import com.loyaltyglobal.data.model.request.LoginRequest
import com.loyaltyglobal.data.model.response.LoginResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Body
import retrofit2.http.POST


interface ApiService {

    @GET("pass/mobile/wallet/agency")
    suspend fun getUserPassFromAgency(@Query("agencyId") agencyId : String) : Response<UserPassResponse>

    @GET("brand/mobile/sharedLoyality/subBrand")
    suspend fun getSubBrand(@Query("agencyId") agencyId : String) : Response<SubBrandResponse>

    @POST("auth/mobile/wallet")
    suspend fun login(@Body loginRequest : LoginRequest) : Response<LoginResponse>
}
