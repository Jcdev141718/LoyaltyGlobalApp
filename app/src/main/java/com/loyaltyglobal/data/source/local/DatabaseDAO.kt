package com.loyaltyglobal.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.loyaltyglobal.data.source.localModels.DollarPointModel
import com.loyaltyglobal.data.source.localModels.subBrandResponse.CustomField
import com.loyaltyglobal.data.source.localModels.subBrandResponse.Notification
import com.loyaltyglobal.data.source.localModels.subBrandResponse.Pass
import com.loyaltyglobal.data.source.localModels.subBrandResponse.Tier

/**
 * Created by Abhin.
 */

@Dao
interface DatabaseDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCustomFields(customField : ArrayList<CustomField>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotification(notifications : ArrayList<Notification>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPass(pass : Pass)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTiers(tier : ArrayList<Tier>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDollarPoint(dollarPointModel : DollarPointModel)
}