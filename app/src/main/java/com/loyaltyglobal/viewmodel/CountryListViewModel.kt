package com.loyaltyglobal.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loyaltyglobal.data.model.CountryCodeData
import com.loyaltyglobal.util.CountryList.setCountryList
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by Abhin.
 */
@HiltViewModel
class CountryListViewModel @Inject constructor() : ViewModel() {

    var countryList = MutableLiveData(ArrayList<CountryCodeData>())

    fun getCountryList() {
        countryList.value?.addAll(setCountryList())
    }
}
