package com.loyaltyglobal.data.source.localModels.subBrandResponse

data class Location(
    var address: String? = null,
    var city: String? = null,
    var country: String? = null,
    var countryCode: String? = null,
    var lat: Double? = null,
    var lng: Double? = null,
    var offset: Int? = null,
    var state: String? = null
)