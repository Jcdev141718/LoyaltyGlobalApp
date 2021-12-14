package com.loyaltyglobal.data.source.localModels.subBrandResponse

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Coalition(
    @PrimaryKey var _id: String,
    var agencyId: String? = null,
    var availDealOffer: Boolean? = null,
    var childBrandId: String? = null,
    var coalitionType: String? = null,
    var created: Long? = null,
    @Embedded(prefix = "coalition_created_at_") var createdAt: CreatedAt? = null,
    var creator: String? = null,
    var delete: Boolean? = null,
    var parentBrandId: String? = null,
    var sharedLoyaltyCampaignId: String? = null,
    @Embedded(prefix = "coalition_tiers_") var tiers: Tiers? = null,
    var updated: Long? = null,
    @Embedded(prefix = "coalition_updated_at_")var updatedAt: UpdatedAt? = null
)