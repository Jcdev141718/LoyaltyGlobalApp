package com.loyaltyglobal.data.source.localModels.subBrandResponse

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Agency(
    var id: String? = null,
    var isMyBrand: Boolean? = null,
    var subscribed: Boolean? = null,
    var subscribedAt: Long? = null
) : Parcelable