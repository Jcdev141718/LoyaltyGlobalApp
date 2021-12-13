package com.loyaltyglobal.ui.main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.loyaltyglobal.data.reposotory.AuthRepository
import com.loyaltyglobal.data.source.network.NetworkResult
import com.loyaltyglobal.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import javax.inject.Inject

/**
 * Created by Abhin.
 */

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : BaseViewModel() {

    var logInResponse : MutableLiveData<NetworkResult<ResponseBody>> = MutableLiveData()

    fun logIn() {
        logInResponse.postValue(NetworkResult.Loading())
        viewModelScope.launch {
            logInResponse.postValue(authRepository.login())
        }
    }
}
