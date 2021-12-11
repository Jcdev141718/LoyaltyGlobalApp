package com.loyaltyglobal.data.source.localModels.subBrandResponse

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CustomField(
    @PrimaryKey var _id: String? = null,
    var agencyId: String? = null,
    var brandId: String? = null,
    var childBrandId: String? = null,
    var created: Long? = null,
    var createdAt: CreatedAt? = null,
    var customFieldType: String? = null,
    var delete: Boolean? = null,
    var image: String? = null,
    var key: String? = null,
    var reference: String? = null,
    var type: String? = null,
    var updated: Long? = null,
    var updatedAt: UpdatedAt? = null,
    var value: String? = null
)