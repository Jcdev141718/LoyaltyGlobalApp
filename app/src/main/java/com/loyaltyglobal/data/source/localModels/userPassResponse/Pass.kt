package com.loyaltyglobal.data.source.localModels.userPassResponse

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Pass(
    @PrimaryKey var _id: String,
    var brandId: String? = null,
    var campaignId: String? = null,
    var created: Long? = null,
    @Embedded(prefix = "pass_created_at_") var createdAt: CreatedAt? = null,
    var daysLeft: Int? = null,
    var delete: Boolean? = null,
    @Embedded(prefix = "loyalty_card_") var loyaltyCard: LoyaltyCard? = null,
    var passCode: String? = null,
    var passDesignId: String? = null,
    var passType: String? = null,
    var unsubscribed: Boolean? = null,
    var updated: Long? = null,
    @Embedded(prefix = "pass_updated_at_") var updatedAt: UpdatedAt? = null,
    var userId: String? = null,
    var wallet: Boolean? = null
) {

    fun getRemainingPoints(maxPoints : Int) : Int? {
        return loyaltyCard?.currentPoints?.let { maxPoints.minus(it) }
    }
}