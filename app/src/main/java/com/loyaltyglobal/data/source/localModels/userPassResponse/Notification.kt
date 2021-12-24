package com.loyaltyglobal.data.source.localModels.userPassResponse

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Notification(
    @PrimaryKey var _id: String,
    var agencyId: String? = null,
    @Embedded(prefix = "notification_background_color_") var backgroundColor: BackgroundColor? = null,
    var brandId: String? = null,
    var campaignId: String? = null,
    var created: Long? = null,
    @Embedded(prefix = "notification_created_at_") var createdAt: CreatedAt? = null,
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
    @Embedded(prefix = "notification_updated_at_") var updatedAt: UpdatedAt? = null,
    var branName : String? = null,
    var brandLogo : String? = null,
    var isOpenedOnce : Boolean = false
)