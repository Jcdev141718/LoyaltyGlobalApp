package com.loyaltyglobal.data.source.localModels.userPassResponse

data class Data(
    var customFields: ArrayList<CustomField>? = null,
    var notification: ArrayList<Notification>? = null,
    var pass: Pass? = null,
    var perDollarPoint: Int? = null,
    var tiers: ArrayList<Tier>? = null
)