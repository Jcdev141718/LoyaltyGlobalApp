package com.loyaltyglobal.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.loyaltyglobal.data.source.localModels.DollarPointModel
import com.loyaltyglobal.data.source.localModels.SubBrandModel
import com.loyaltyglobal.data.source.localModels.subBrandResponse.Coalition
import com.loyaltyglobal.data.source.localModels.subBrandResponse.SubBrand
import com.loyaltyglobal.data.source.localModels.userPassResponse.CustomField
import com.loyaltyglobal.data.source.localModels.userPassResponse.Notification
import com.loyaltyglobal.data.source.localModels.userPassResponse.Pass
import com.loyaltyglobal.data.source.localModels.userPassResponse.Tier

/**
 * Created by Abhin.
 */

@Database(entities = [Tier::class, SubBrandModel::class, CustomField::class, Notification::class, Pass::class, DollarPointModel::class, Coalition::class, SubBrand::class],
    version = 2,
    exportSchema = false)
@TypeConverters(TypeConverterHelper::class)
abstract class LocalDatabase : RoomDatabase() {
    abstract fun dataBaseDao(): DatabaseDAO
}