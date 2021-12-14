package com.loyaltyglobal.data.source.localModels.subBrandResponse

import androidx.room.Embedded

data class Tiers(
    @Embedded(prefix = "benefits_") var benefits: Benefits? = null,
    var id: String? = null,
    var name: String? = null
)