package com.loyaltyglobal.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.loyaltyglobal.data.source.localModels.DollarPointModel
import com.loyaltyglobal.data.source.localModels.userPassResponse.CustomField
import com.loyaltyglobal.data.source.localModels.userPassResponse.Notification
import com.loyaltyglobal.data.source.localModels.userPassResponse.Pass
import com.loyaltyglobal.data.source.localModels.userPassResponse.Tier
import com.loyaltyglobal.data.source.localModels.subBrandResponse.Coalition
import com.loyaltyglobal.data.source.localModels.subBrandResponse.SubBrand

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
}