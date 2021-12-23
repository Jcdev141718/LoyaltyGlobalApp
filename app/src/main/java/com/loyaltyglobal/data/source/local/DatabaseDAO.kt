package com.loyaltyglobal.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.loyaltyglobal.data.source.localModels.DollarPointModel
import com.loyaltyglobal.data.source.localModels.subBrandResponse.Coalition
import com.loyaltyglobal.data.source.localModels.subBrandResponse.DealOffer
import com.loyaltyglobal.data.source.localModels.subBrandResponse.SubBrand
import com.loyaltyglobal.data.source.localModels.userPassResponse.CustomField
import com.loyaltyglobal.data.source.localModels.userPassResponse.Notification
import com.loyaltyglobal.data.source.localModels.userPassResponse.Pass
import com.loyaltyglobal.data.source.localModels.userPassResponse.Tier
import com.loyaltyglobal.util.Constants.CUSTOM_FIELD_TYPE_IMAGE
import com.loyaltyglobal.util.Constants.NOTIFICATION_TYPE_IMAGE
import com.loyaltyglobal.util.Constants.NOTIFICATION_TYPE_TEXT

/**
 * Created by Abhin.
 */

@Dao
interface DatabaseDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCustomFields(customField: ArrayList<CustomField>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotification(notifications: ArrayList<Notification>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPass(pass: Pass)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTiers(tier: ArrayList<Tier>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDollarPoint(dollarPointModel: DollarPointModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCoalition(coalition: ArrayList<Coalition>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSubBrand(coalition: ArrayList<SubBrand>)

    @Query("select count() from SubBrand")
    suspend fun countOfSubBrands() : Int

    @Query("select count() from CustomField")
    suspend fun countOfCustomFields() : Int

    @Query("select sub_brand_deal_offer_name,sub_brand_deal_offer_image,sub_brand_deal_offer_dealDescription from SubBrand where _id in (select childBrandId from coalition where availDealOffer = 0)")
    suspend fun getDealsAndOffers() : List<DealOffer>

    @Query("select * from Notification where type == '$NOTIFICATION_TYPE_TEXT' or type == '$NOTIFICATION_TYPE_IMAGE'")
    suspend fun getStoriesList() : List<Notification>

    @Query("select * from CustomField where type == '$CUSTOM_FIELD_TYPE_IMAGE'")
    suspend fun getCustomFieldList() : List<CustomField>

    @Query("select _id,brandName,brandLogo from SubBrand")
    suspend fun getSubBrandNameAndLogo() : List<SubBrand>
}