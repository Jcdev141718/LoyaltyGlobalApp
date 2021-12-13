package com.loyaltyglobal.ui.main.viewmodel

import android.content.Context
import android.telephony.TelephonyManager
import androidx.lifecycle.MutableLiveData
import com.loyaltyglobal.R
import com.loyaltyglobal.data.model.CountryCodeData
import com.loyaltyglobal.ui.base.BaseViewModel
import com.loyaltyglobal.util.CountryList
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class VerificationViewModel @Inject constructor(): BaseViewModel(){

    lateinit var mCountryData: CountryCodeData
    lateinit var mMobileNumber: String
    var otpResponse : MutableLiveData<OtpResponse> = MutableLiveData()

    fun getCurrentCountryData(context: Context) {
        val tm = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        mCountryData = CountryList.getCountryDataByCode(tm.networkCountryIso)!!
    }

    fun sendOtp(context: Context, mobileNumber: String) {
        when {
            mobileNumber.isEmpty() -> {
                otpResponse.postValue(OtpResponse.Error(context.getString(R.string.please_enter_mobile_number)))
            }
            mobileNumber.length < 10 -> {
                otpResponse.postValue(OtpResponse.Error(context.getString(R.string.invalid_mobile_number)))
            }
            else -> {
                mMobileNumber = "${mCountryData.countryCode}${mobileNumber}"
                otpResponse.postValue(OtpResponse.Success)
            }
        }
    }
}
sealed class OtpResponse(var message: String? = null) {
    object Success : OtpResponse()
    class Error(message: String) : OtpResponse(message)
}
