package com.loyaltyglobal.data.model.response

data class HomeScreenStoriesData(
    var brandName: String? = null,
    var brandImageUrl: String? = null,
    var description: String? = null,
    var isLoading: Boolean = false,
    var thumbnailUrl: String? = null
)
