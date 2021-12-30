package com.loyaltyglobal.data.source.localModels.subBrandResponse

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class WalletApp(
    var android: String? = null,
    var apple: String? = null
) : Parcelable