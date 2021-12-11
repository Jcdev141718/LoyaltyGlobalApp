package com.loyaltyglobal.data.source.localModels

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Abhin.
 */

@Entity
data class DollarPointModel(
    @PrimaryKey var id : Int? = null,
    var perDollarPoint : Int? = null
)
