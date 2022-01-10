package com.loyaltyglobal.data.model.response

data class Data(
    var _id: String? = null,
    var birthday: Long? = null,
    var created: Long? = null,
    var createdAt: CreatedAt? = null,
    var delete: Boolean? = null,
    var dialingCode: String? = null,
    var email: String? = null,
    var firstName: String? = null,
    var isNumberVerified: Boolean? = null,
    var lastName: String? = null,
    var location: Location? = null,
    var password: String? = null,
    var phone: String? = null,
    var platforms: Platforms? = null,
    var roles: List<String> = ArrayList(),
    var token: String? = null,
    var updated: Long? = null,
    var updatedAt: UpdatedAt? = null,
    var verificationCode: String? = null,
    var verified: Boolean? = null
)
