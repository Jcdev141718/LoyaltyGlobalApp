package com.loyaltyglobal.data.source.localModels.userPassResponse

import androidx.room.Embedded

data class LoyaltyCard(
    var currentPoints: Int = 0,
    @Embedded var currentTier: CurrentTier? = null,
    var lastAmountSpent: Int? = null,
    var lastRedeemAt: Long? = null,
    var redeemCount: Int? = null,
    var totalPoints: Int? = null,
    var totalSpending: Int? = null
)