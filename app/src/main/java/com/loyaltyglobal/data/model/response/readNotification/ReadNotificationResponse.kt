package com.loyaltyglobal.data.model.response.readNotification

data class ReadNotificationResponse(
    var `data`: Data? = null,
    var message: String? = null,
    var success: Boolean? = null
)