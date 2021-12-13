package com.loyaltyglobal.data.model.request

data class LoginRequest(
    val agencyId: String,
    val appleAuthToken: String,
    val email: String,
    val loginType: String,
    val platform: String
)
