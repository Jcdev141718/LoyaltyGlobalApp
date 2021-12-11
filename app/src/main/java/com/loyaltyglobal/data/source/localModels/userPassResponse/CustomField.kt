package com.loyaltyglobal.data.source.localModels.userPassResponse

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CustomField(
    @PrimaryKey var _id: String,
    var agencyId: String? = null,
    var brandId: String? = null,
    var childBrandId: String? = null,
    var created: Long? = null,
    @Embedded(prefix = "custom_field_created_at_") var createdAt: CreatedAt? = null,
    var customFieldType: String? = null,
    var delete: Boolean? = null,
    var image: String? = null,
    var key: String? = null,
    var reference: String? = null,
    var type: String? = null,
    var updated: Long? = null,
    @Embedded(prefix = "custom_field_updated_at_") var updatedAt: UpdatedAt? = null,
    var value: String? = null,
)