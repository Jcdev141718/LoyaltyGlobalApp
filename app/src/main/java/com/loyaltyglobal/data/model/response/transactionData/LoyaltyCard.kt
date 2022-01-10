package com.loyaltyglobal.data.model.response.transactionData

data class LoyaltyCard(
    val amount: Int,
    val benefits: Benefits,
    val discountValue: Int,
    val pointUsed: Int,
    val points: Int
)
