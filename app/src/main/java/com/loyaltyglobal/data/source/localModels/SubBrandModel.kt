package com.loyaltyglobal.data.source.localModels

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Abhin.
 */

@Entity
data class SubBrandModel(
    @PrimaryKey(autoGenerate = true) var id: Int
)