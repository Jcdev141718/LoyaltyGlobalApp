package com.loyaltyglobal.data.model.response

data class Data(
    val _id: String,
    val birthday: Long,
    val created: Long,
    val createdAt: CreatedAt,
    val delete: Boolean,
    val dialingCode: String,
    val email: String,
    val firstName: String,
    val isNumberVerified: Boolean,
    val lastName: String,
    val location: Location,
    val password: String,
    val phone: String,
    val platforms: Platforms,
    val roles: List<String>,
    val token: String,
    val updated: Long,
    val updatedAt: UpdatedAt,
    val verificationCode: String,
    val verified: Boolean
)
