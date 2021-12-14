package com.loyaltyglobal.ui.main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.loyaltyglobal.data.model.request.LoginRequest
import com.loyaltyglobal.data.model.response.LoginResponse
import com.loyaltyglobal.data.reposotory.AuthRepository
import com.loyaltyglobal.data.source.network.NetworkResult
import com.loyaltyglobal.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : BaseViewModel() {

    var logInResponse : MutableLiveData<NetworkResult<LoginResponse>> = MutableLiveData()

    fun logIn(loginRequest: LoginRequest) {
        logInResponse.postValue(NetworkResult.Loading())
        viewModelScope.launch {
            logInResponse.postValue(authRepository.getLogin(loginRequest))
        }
    }
}
