package com.loyaltyglobal.ui.main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.loyaltyglobal.data.model.response.readNotification.ReadNotificationResponse
import com.loyaltyglobal.data.reposotory.HomeRepository
import com.loyaltyglobal.data.source.localModels.NotificationAndSubBrand
import com.loyaltyglobal.data.source.network.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Abhin.
 */
@HiltViewModel
class NotificationViewModel @Inject constructor(private val homeRepository: HomeRepository) : ViewModel() {

    var mutableNotificationList = MutableLiveData<MutableList<NotificationAndSubBrand>>()
    var mReadNotificationResponse = MutableLiveData<NetworkResult<ReadNotificationResponse>>()

    fun getNotificationList() {
        viewModelScope.launch {
            mutableNotificationList.value = homeRepository.getAllNotifications()
        }
    }

    fun readNotification(notificationId: String) {
        viewModelScope.launch {
            mReadNotificationResponse.postValue(homeRepository.readNotification(notificationId))
        }
    }
}
