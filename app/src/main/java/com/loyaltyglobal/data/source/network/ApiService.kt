package com.loyaltyglobal.data.source.network

import com.loyaltyglobal.data.model.request.LoginRequest
import com.loyaltyglobal.data.model.response.Data
import com.loyaltyglobal.data.model.response.LoginResponse
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST


interface ApiService {

    @POST("auth/mobile/wallet")
    suspend fun login(@Body loginRequest : LoginRequest) : Response<LoginResponse>
}
