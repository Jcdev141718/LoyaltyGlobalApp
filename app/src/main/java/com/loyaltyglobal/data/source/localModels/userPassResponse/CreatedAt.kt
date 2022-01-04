package com.loyaltyglobal.data.source.localModels.userPassResponse

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CreatedAt(
    var _nanoseconds: Int? = null,
    var _seconds: Int? = null
): Parcelable