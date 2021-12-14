package com.loyaltyglobal.data.source.localModels.userPassResponse

data class Discount(
    var active: Boolean? = null,
    var customDiscounts: String? = null,
    var discountValue: Int? = null
)