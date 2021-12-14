package com.loyaltyglobal.data.source.localModels.userPassResponse

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Tier(
    @PrimaryKey var _id: String,
    var active: Boolean? = null,
    @Embedded(prefix = "tier_background_color_") var backgroundColor: BackgroundColor? = null,
    @Embedded(prefix = "tier_benefits_") var benefits: Benefits? = null,
    var brandId: String? = null,
    var campaignId: String? = null,
    var created: Long? = null,
    @Embedded(prefix = "tier_created_at_") var createdAt: CreatedAt? = null,
    var delete: Boolean? = null,
    var expirationDate: Long? = null,
    var expirationDays: Int? = null,
    var fontColor: String? = null,
    var name: String? = null,
    @Embedded(prefix = "tier_point_range") var pointRange: PointRange? = null,
)