package com.loyaltyglobal.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loyaltyglobal.data.model.CountryCodeData
import com.loyaltyglobal.util.Constants.setCountryList
import dagger.hilt.android.lifecycle.HiltViewModel

/**
 * Created by Abhin.
 */
@HiltViewModel
class CountryListViewModel : ViewModel() {

    var countryList = MutableLiveData(ArrayList<CountryCodeData>())

    fun getCountryList() {
        countryList.value?.addAll(setCountryList())
    }
}
