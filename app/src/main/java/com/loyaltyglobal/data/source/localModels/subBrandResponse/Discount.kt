package com.loyaltyglobal.data.source.localModels.subBrandResponse

data class Discount(
    var active: Boolean? = null,
    var customDiscounts: List<Int>? = null,
    var discountValue: Int? = null
)