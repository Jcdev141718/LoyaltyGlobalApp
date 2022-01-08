package com.loyaltyglobal.data.source.localModels

import com.loyaltyglobal.util.firstLetterCap

/**
 * Created by Abhin.
 */
data class FilterModel(
    var filterTitle : String? = null,
    var isSelected : Boolean = false
) {
    fun getFilterTitleCap() = filterTitle?.firstLetterCap()
}
