package com.loyaltyglobal.data.source.network

import com.loyaltyglobal.data.source.localModels.subBrandResponse.SubBrandResponse
import com.loyaltyglobal.data.source.localModels.userPassResponse.UserPassResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {

    @GET("pass/mobile/wallet/agency")
    suspend fun getUserPassFromAgency(@Query("agencyId") agencyId : String) : Response<UserPassResponse>

    @GET("brand/mobile/sharedLoyality/subBrand")
    suspend fun getSubBrand(@Query("agencyId") agencyId : String) : Response<SubBrandResponse>
}