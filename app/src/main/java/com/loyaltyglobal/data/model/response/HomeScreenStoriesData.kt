package com.loyaltyglobal.data.model.response

import com.loyaltyglobal.data.source.localModels.userPassResponse.BackgroundColor
import com.loyaltyglobal.data.source.localModels.userPassResponse.UpdatedAt

data class HomeScreenStoriesData(
    var brandName: String? = null,
    var brandImageUrl: String? = null,
    var isLoading: Boolean = false,
    var brandLogo: String? = null,
    var _id: String,
    var brandId: String? = null,
    var backgroundColor: BackgroundColor? = null,
    var campaignId: String? = null,
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
    var updatedAt: UpdatedAt? = null,
)
