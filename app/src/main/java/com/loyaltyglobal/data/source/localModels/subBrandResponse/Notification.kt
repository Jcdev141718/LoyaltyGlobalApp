package com.loyaltyglobal.data.source.localModels.subBrandResponse

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Notification(
    @PrimaryKey var _id: String? = null,
    var agencyId: String? = null,
    var backgroundColor: BackgroundColor? = null,
    var brandId: String? = null,
    var campaignId: String? = null,
    var created: Long? = null,
    var createdAt: CreatedAt? = null,
    var delete: Boolean? = null,
    var description: String? = null,
    var displayTitle: String? = null,
    var foregroundColor: String? = null,
    var imageUrl: String? = null,
    var linkUrl: String? = null,
    var readBy: List<String>? = null,
    var schedule: Boolean? = null,
    var scheduleDate: Long? = null,
    var scheduleDateString: String? = null,
    var sendTo: List<String>? = null,
    var title: String? = null,
    var type: String? = null,
    var updated: Long? = null,
    var updatedAt: UpdatedAt? = null
)