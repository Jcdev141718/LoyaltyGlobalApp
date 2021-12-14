package com.loyaltyglobal.data.source.localModels

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Abhin.
 */

@Entity
data class DollarPointModel(
    @PrimaryKey(autoGenerate = true) var id : Int = 0,
    var perDollarPoint : Int? = null
)
