package com.loyaltyglobal.data.source.network

import com.loyaltyglobal.data.model.request.LoginRequest
import com.loyaltyglobal.data.model.request.ReadNotificationRequest
import com.loyaltyglobal.data.model.request.UpdateUserRequest
import com.loyaltyglobal.data.model.response.LoginResponse
import com.loyaltyglobal.data.model.response.readNotification.ReadNotificationResponse
import com.loyaltyglobal.data.model.response.updateUser.UpdateUserResponse
import com.loyaltyglobal.data.source.localModels.subBrandResponse.SubBrandResponse
import com.loyaltyglobal.data.source.localModels.userPassResponse.UserPassResponse
import retrofit2.Response
import retrofit2.http.*


interface ApiService {

    @GET("pass/mobile/wallet/agency")
    suspend fun getUserPassFromAgency(@Query("agencyId") agencyId : String) : Response<UserPassResponse>

    @GET("brand/mobile/sharedLoyality/subBrand")
    suspend fun getSubBrand(@Query("agencyId") agencyId : String) : Response<SubBrandResponse>

    @POST("auth/mobile/wallet")
    suspend fun login(@Body loginRequest : LoginRequest) : Response<LoginResponse>

    @PUT("user/mobile/wallet")
    suspend fun enableNotification(@Body request: UpdateUserRequest) : Response<UpdateUserResponse>

    @PUT("notifications")
    suspend fun readNotification(@Body request : ReadNotificationRequest) : Response<ReadNotificationResponse>
}
