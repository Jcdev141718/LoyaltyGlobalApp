package com.loyaltyglobal.ui.main.viewmodel

import androidx.lifecycle.viewModelScope
import com.loyaltyglobal.data.reposotory.HomeRepository
import com.loyaltyglobal.data.source.local.DatabaseDAO
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
    private val dataBaseDao: DatabaseDAO
) : BaseViewModel() {

    fun getUserPassFromAgency() {
        viewModelScope.launch(coroutineScope) {
            try {
                val response = homeRepository.getUserPassFromAgency()
                response.responseData?.data?.let { data ->
                    data.customFields?.let { dataBaseDao.insertCustomFields(it) }
                    data.pass?.let { dataBaseDao.insertPass(it) }
                    data.notification?.let { dataBaseDao.insertNotification(it) }
                    data.tiers?.let { dataBaseDao.insertTiers(it) }
                    data.perDollarPoint?.let { dataBaseDao.insertDollarPoint(DollarPointModel(perDollarPoint = it)) }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun getSubBrands() {
        viewModelScope.launch(coroutineScope) {
            try {
                val response = homeRepository.getSubBrand()
                response.responseData?.let { data ->
                    data.coalition?.let { dataBaseDao.insertCoalition(it) }
                    data.subBrands?.let { dataBaseDao.insertSubBrand(it) }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
