package com.loyaltyglobal.data.source.localModels.subBrandResponse

import androidx.room.ColumnInfo

data class DealOffer(
    @ColumnInfo(name = "sub_brand_deal_offer_dealDescription") var dealDescription: String? = null,
    @ColumnInfo(name = "sub_brand_deal_offer_image") var image: String? = null,
    @ColumnInfo(name = "sub_brand_deal_offer_name") var name: String? = null,
)