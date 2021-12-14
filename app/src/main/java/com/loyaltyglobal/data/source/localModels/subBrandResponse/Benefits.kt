package com.loyaltyglobal.data.source.localModels.subBrandResponse

import androidx.room.Embedded

data class Benefits(
    @Embedded(prefix = "benefits_discount_") var discount: Discount? = null,
    @Embedded(prefix = "benefits_points_") var points: Points? = null
)