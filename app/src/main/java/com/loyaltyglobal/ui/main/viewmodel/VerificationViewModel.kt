package com.loyaltyglobal.ui.main.viewmodel

import android.content.Context
import android.telephony.TelephonyManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.loyaltyglobal.R
import com.loyaltyglobal.data.model.CountryCodeData
import com.loyaltyglobal.data.model.response.updateUser.UpdateUserResponse
import com.loyaltyglobal.data.reposotory.AuthRepository
import com.loyaltyglobal.data.source.network.NetworkResult
import com.loyaltyglobal.ui.base.BaseViewModel
import com.loyaltyglobal.util.Constants.MINIMUM_LENGTH_OF_NUMBER
import com.loyaltyglobal.util.CountryList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VerificationViewModel @Inject constructor(
    private var authRepository: AuthRepository
): BaseViewModel(){

    var mCountryData: CountryCodeData?=null
    lateinit var mMobileNumber: String
    var otpResponse : MutableLiveData<OtpResponse> = MutableLiveData()
    var updateUserResponse : MutableLiveData<NetworkResult<UpdateUserResponse>> = MutableLiveData()

    fun getCurrentCountryData(context: Context) {
        val tm = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        CountryList.getCountryDataByCode(tm.networkCountryIso)?.let {
            mCountryData = it
        }
    }

    fun sendOtp(context: Context, mobileNumber: String) {
        when {
            mobileNumber.isEmpty() -> {
                otpResponse.postValue(OtpResponse.Error(context.getString(R.string.please_enter_mobile_number)))
            }
            mobileNumber.length < MINIMUM_LENGTH_OF_NUMBER -> {
                otpResponse.postValue(OtpResponse.Error(context.getString(R.string.invalid_mobile_number_length, MINIMUM_LENGTH_OF_NUMBER)))
            }
            else -> {
                mMobileNumber = "${mCountryData?.countryCode}${mobileNumber}"
                otpResponse.postValue(OtpResponse.Success)
            }
        }
    }

    fun enableNotification(oneSignalId : String) {
        updateUserResponse.postValue(NetworkResult.Loading())
        viewModelScope.launch(Dispatchers.IO) {
            updateUserResponse.postValue(authRepository.enableNotification(oneSignalId))
        }
    }
}
sealed class OtpResponse(var message: String? = null) {
    object Success : OtpResponse()
    class Error(message: String) : OtpResponse(message)
}
