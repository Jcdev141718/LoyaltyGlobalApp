package com.loyaltyglobal.data.source.localModels.subBrandResponse

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Webhook(
    var url: String? = null
) : Parcelable