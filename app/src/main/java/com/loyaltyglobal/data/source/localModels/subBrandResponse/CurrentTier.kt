package com.loyaltyglobal.data.source.localModels.subBrandResponse

data class CurrentTier(
    var currentTierId: String? = null,
    var currentTierName: String? = null,
    var expirationDate: Long? = null,
    var startDate: Long? = null
)