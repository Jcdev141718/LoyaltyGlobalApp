package com.loyaltyglobal.data.source.localModels

import androidx.room.Embedded
import androidx.room.Relation
import com.loyaltyglobal.data.source.localModels.subBrandResponse.Coalition
import com.loyaltyglobal.data.source.localModels.subBrandResponse.SubBrand

/**
 * Created by Abhin.
 */
data class SubBrandAndCoalition(
    @Embedded val subBrand: SubBrand,
    @Relation(parentColumn = "_id", entityColumn = "childBrandId") val coalition: Coalition,
)