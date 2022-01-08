package com.loyaltyglobal.data.source.localModels.subBrandResponse

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.loyaltyglobal.util.firstLetterCap
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class SubBrand(
    @PrimaryKey var _id: String,
    @Embedded(prefix = "sub_brand_agency") var agency: Agency? = null,
    var apiKey: String? = null,
    var avgSpendingValue: Int? = null,
    var brandColor: String? = null,
    var brandCover: String? = null,
    var brandLogo: String? = null,
    var brandName: String? = null,
    var brandRedemptionType: String? = null,
    var brandType: String? = null,
    var businessService: String? = null,
    var created: Long? = null,
    @Embedded(prefix = "sub_brand_created_at_") var createdAt: CreatedAt? = null,
    var createdBy: String? = null,
    @Embedded(prefix = "sub_brand_currency_") var currency: Currency? = null,
    @Embedded var dealOffer: DealOffer? = null,
    var delete: Boolean? = null,
    var description: String? = null,
    var dialingCode: String? = null,
    var earnPointPer: Int? = null,
    var email: String? = null,
    @Embedded(prefix = "sub_brand_integrations_") var integrations: Integrations? = null,
    @Embedded(prefix = "sub_brand_location_") var location: Location? = null,
    var locationType: String? = null,
    var onlineService: String? = null,
    var owner: String? = null,
    var parentBrandId: String? = null,
    var phone: String? = null,
    var platform: String? = null,
    var platformId: String? = null,
    @Embedded(prefix = "sub_brand_selectedCampaign_") var selectedCampaign: SelectedCampaign? = null,
    @Embedded(prefix = "sub_brand_social_") var social: Social? = null,
    var updated: Long? = null,
    @Embedded(prefix = "sub_brand_updated_at_") var updatedAt: UpdatedAt? = null,
    @Embedded(prefix = "sub_brand_walletApp_") var walletApp: WalletApp? = null,
) : Parcelable {
    fun getBrandNameFirstCap() = brandName?.firstLetterCap()
    fun getLocationTypeFirstCap() = locationType?.firstLetterCap()
    fun getSafePointsPer() = earnPointPer ?: 0
}
