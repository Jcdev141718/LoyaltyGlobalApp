package com.loyaltyglobal.data.source.local

import androidx.room.*
import com.loyaltyglobal.data.source.localModels.DollarPointModel
import com.loyaltyglobal.data.source.localModels.LinkKeyValueModel
import com.loyaltyglobal.data.source.localModels.SubBrandAndCoalition
import com.loyaltyglobal.data.source.localModels.NotificationAndSubBrand
import com.loyaltyglobal.data.source.localModels.subBrandResponse.Coalition
import com.loyaltyglobal.data.source.localModels.subBrandResponse.DealOffer
import com.loyaltyglobal.data.source.localModels.subBrandResponse.SubBrand
import com.loyaltyglobal.data.source.localModels.userPassResponse.CustomField
import com.loyaltyglobal.data.source.localModels.userPassResponse.Notification
import com.loyaltyglobal.data.source.localModels.userPassResponse.Pass
import com.loyaltyglobal.data.source.localModels.userPassResponse.Tier
import com.loyaltyglobal.util.Constants
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

    @Query("select count() from Pass")
    suspend fun countOfPassData() : Int

    @Query("select count() from CustomField")
    suspend fun countOfCustomFields() : Int

    @Query("select sub_brand_deal_offer_name,sub_brand_deal_offer_image,sub_brand_deal_offer_dealDescription from SubBrand where _id in (select childBrandId from Coalition where availDealOffer = '0') and `delete` = '0'")
    suspend fun getDealsAndOffers() : List<DealOffer>

    @Query("select * from Notification where type == '$NOTIFICATION_TYPE_TEXT' or type == '$NOTIFICATION_TYPE_IMAGE'")
    suspend fun getStoriesList() : List<Notification>

    @Query("select * from CustomField where type == '$CUSTOM_FIELD_TYPE_IMAGE' and `delete` == '0'")
    suspend fun getCustomFieldList() : List<CustomField>

    @Query("select _id,brandName,brandLogo from SubBrand where `delete` == '0'")
    suspend fun getSubBrandNameAndLogo() : List<SubBrand>

    @Query("update Notification set isOpenedOnce = '1' where _id = :id")
    suspend fun updateStoryItem(id : String)

    @Query("select * from Pass where userId = :userId")
    suspend fun getPassData(userId : String) : Pass

    @Query("select daysLeft from Pass where userId = :userId")
    suspend fun getDaysLeft(userId : String) : Int

    @Query("select * from Tier where _id = (select loyalty_card_currentTierId from Pass where userId = :userId)")
    suspend fun getPointsDataFromTiers(userId : String) : Tier

    @Query("update Notification set readBy = :readBy where _id = :id")
    suspend fun readNotification(id : String,readBy : List<String>)

    @Update
    suspend fun updateNotification(notification : Notification)

    @Query("select distinct locationType from SubBrand where `delete` = '0'")
    suspend fun getFilters() : List<String>

    @Transaction
    @Query("select * from SubBrand where `delete` = '0'")
    suspend fun getSubBrandWithCoalitionData(): List<SubBrandAndCoalition>

    @Query("select `key`,value from CustomField where type = '${Constants.URL}' and childBrandId = :id")
    suspend fun getKeyValueData(id : String) : List<LinkKeyValueModel>

    @Query("select * from Notification where type = 'pointsUpdateNotification' or type = 'brandAddNotification' order by created desc")
    suspend fun getAllNotifications() : List<NotificationAndSubBrand>

    @Query("select * from SubBrand where `delete` = '0' and _id = :brandId")
    suspend fun getSubBrandWithCoalitionDataById(brandId :String): SubBrandAndCoalition
}
