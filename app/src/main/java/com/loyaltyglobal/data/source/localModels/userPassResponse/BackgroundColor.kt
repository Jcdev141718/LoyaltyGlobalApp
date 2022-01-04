package com.loyaltyglobal.data.source.localModels.userPassResponse

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BackgroundColor(
    var endColor: String? = null,
    var startColor: String? = null,
    var middleColor: String? = null,
): Parcelable