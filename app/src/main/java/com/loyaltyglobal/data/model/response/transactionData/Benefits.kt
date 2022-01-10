package com.loyaltyglobal.data.model.response.transactionData

data class Benefits(
    val coalitionId: String,
    val dealOffer: DealOffer,
    val tierDiscountBenefit: Int,
    val tierDisountType: String,
    val tierPointBenefit: Int
)
