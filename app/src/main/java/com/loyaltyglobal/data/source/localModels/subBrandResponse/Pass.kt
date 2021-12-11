package com.loyaltyglobal.data.source.localModels.subBrandResponse

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Pass(
    @PrimaryKey var _id: String? = null,
    var brandId: String? = null,
    var campaignId: String? = null,
    var created: Long? = null,
    var createdAt: CreatedAt? = null,
    var daysLeft: Int? = null,
    var delete: Boolean? = null,
    var loyaltyCard: LoyaltyCard? = null,
    var passCode: String? = null,
    var passDesignId: String? = null,
    var passType: String? = null,
    var unsubscribed: Boolean? = null,
    var updated: Long? = null,
    var updatedAt: UpdatedAt? = null,
    var userId: String? = null,
    var wallet: Boolean? = null
)