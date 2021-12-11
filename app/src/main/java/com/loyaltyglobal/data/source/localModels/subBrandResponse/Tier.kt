package com.loyaltyglobal.data.source.localModels.subBrandResponse

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Tier(
    @PrimaryKey var _id: String? = null,
    var active: Boolean? = null,
    var backgroundColor: BackgroundColor? = null,
    var benefits: Benefits? = null,
    var brandId: String? = null,
    var campaignId: String? = null,
    var created: Long? = null,
    var createdAt: CreatedAt? = null,
    var delete: Boolean? = null,
    var expirationDate: Long? = null,
    var expirationDays: Int? = null,
    var fontColor: String? = null,
    var name: String? = null,
    var pointRange: PointRange? = null,
)