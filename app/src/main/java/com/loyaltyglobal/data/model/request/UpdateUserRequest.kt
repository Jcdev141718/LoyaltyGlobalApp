package com.loyaltyglobal.data.model.request

data class UpdateUserRequest(
    var about: String? = null,
    var agencyId: String? = null,
    var dialingCode: String? = null,
    var firstName: String? = null,
    var isNumberVerified: String? = null,
    var lastName: String? = null,
    var latitude: String? = null,
    var locationName: String? = null,
    var longitude: String? = null,
    var oneSignalId: String? = null,
    var phoneNumber: String? = null,
    var platform: String? = null,
    var userImageUrl: String? = null,
    var verified: String? = null
)