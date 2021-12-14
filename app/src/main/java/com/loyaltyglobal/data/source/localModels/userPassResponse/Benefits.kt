package com.loyaltyglobal.data.source.localModels.userPassResponse

import androidx.room.Embedded

data class Benefits(
    @Embedded(prefix = "discount_")var discount: Discount? = null,
    @Embedded(prefix = "points_") var points: Points? = null
)