package com.loyaltyglobal.data.source.network

import okhttp3.ResponseBody
import retrofit2.Response


interface ApiService {


    suspend fun login() : Response<ResponseBody>
}
