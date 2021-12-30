package com.loyaltyglobal.data.source.localModels.subBrandResponse

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Social(
    var facebook: String? = null,
    var instagram: String? = null,
    var website: String? = null
) : Parcelable