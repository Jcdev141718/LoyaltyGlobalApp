package com.loyaltyglobal.data.source.network

import com.loyaltyglobal.data.source.localModels.subBrandResponse.SubBrandResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {

    @GET("brand/mobile/sharedLoyality/subBrand")
    suspend fun getSubBrand(@Query("agencyId") agencyId : String) : Response<SubBrandResponse>
}