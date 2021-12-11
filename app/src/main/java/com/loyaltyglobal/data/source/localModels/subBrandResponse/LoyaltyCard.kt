package com.loyaltyglobal.data.source.localModels.subBrandResponse

data class LoyaltyCard(
    var currentPoints: Int? = null,
    var currentTier: CurrentTier? = null,
    var lastAmountSpent: Int? = null,
    var lastRedeemAt: Long? = null,
    var redeemCount: Int? = null,
    var totalPoints: Int? = null,
    var totalSpending: Int? = null
)