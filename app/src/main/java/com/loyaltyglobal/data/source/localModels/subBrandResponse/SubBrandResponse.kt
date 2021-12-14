package com.loyaltyglobal.data.source.localModels.subBrandResponse

data class SubBrandResponse(
    var coalition: ArrayList<Coalition>? = null,
    var error: String? = null,
    var message: String? = null,
    var subBrands: ArrayList<SubBrand>? = null,
    var success: Boolean? = null
)