package com.loyaltyglobal.ui.main.viewmodel

import androidx.lifecycle.viewModelScope
import com.loyaltyglobal.data.reposotory.HomeRepository
import com.loyaltyglobal.data.source.localModels.DollarPointModel
import com.loyaltyglobal.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Abhin.
 */

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository,
) : BaseViewModel() {

    fun getSubBrands() {
        viewModelScope.launch(coroutineScope) {
            val response = homeRepository.getSubBrand()
            response.responseData?.data?.let { data ->
                data.customFields?.let { dataBaseDao.insertCustomFields(it) }
                data.pass?.let { dataBaseDao.insertPass(it) }
                data.notification?.let { dataBaseDao.insertNotification(it) }
                data.tiers?.let { dataBaseDao.insertTiers(it) }
                data.perDollarPoint?.let { dataBaseDao.insertDollarPoint(DollarPointModel(it)) }
            }
        }
    }
}