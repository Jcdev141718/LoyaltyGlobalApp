package com.loyaltyglobal.data.source.localModels.subBrandResponse

data class Discount(
    var active: Boolean? = null,
    var customDiscounts: List<Int> = ArrayList(),
    var discountValue: Int? = null
)