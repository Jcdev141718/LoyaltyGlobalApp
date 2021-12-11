package com.loyaltyglobal.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.loyaltyglobal.data.source.localModels.SubBrandModel
import com.loyaltyglobal.data.source.localModels.subBrandResponse.CustomField
import com.loyaltyglobal.data.source.localModels.subBrandResponse.Notification
import com.loyaltyglobal.data.source.localModels.subBrandResponse.Pass
import com.loyaltyglobal.data.source.localModels.subBrandResponse.Tier

/**
 * Created by Abhin.
 */

@Database(entities = [Tier::class, SubBrandModel::class, CustomField::class, Notification::class, Pass::class],
    version = 1,
    exportSchema = false) //@TypeConverters(TypeConverterHelper::class)
abstract class LocalDatabase : RoomDatabase() {
    abstract fun dataBaseDao(): DatabaseDAO
}