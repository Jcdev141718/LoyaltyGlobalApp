package com.loyaltyglobal.data.model.response.transactionData

data class Data(
    var _id: String? =null,
    var brandId: String? =null,
    var campaignId: String? =null,
    var childBrandId: String? =null,
    var created: Long? =null,
    var createdAt: CreatedAt? =null,
    var customerName: String? =null,
    var delete: Boolean? =null,
    var employerId: String? =null,
    var location: Location? =null,
    var loyaltyCard: LoyaltyCard? =null,
    var passId: String? =null,
    var passType: String? =null,
    var transactionType: String? =null,
    var userId: String? =null,
    var brandName : String? = null,
    var brandLogo : String? = null
)
