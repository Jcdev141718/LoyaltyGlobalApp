package com.loyaltyglobal.ui.main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loyaltyglobal.data.model.DealsAndOffersData

/**
 * Created by Abhin.
 */
class DealsAndOffersViewModel : ViewModel() {

    var mutableDealsAndOffersList = MutableLiveData<ArrayList<DealsAndOffersData>>()

    fun getDealsAndOffersList() {
        val list = ArrayList<DealsAndOffersData>()
        list.add(DealsAndOffersData(brandName = "Brand name", discount = "Get 10% entire menu"))
        list.add(DealsAndOffersData(brandName = "Brand name", discount = "Get 10% entire menu"))
        list.add(DealsAndOffersData(brandName = "Brand name", discount = "Get 10% entire menu"))
        list.add(DealsAndOffersData(brandName = "Brand name", discount = "Get 10% entire menu"))
        list.add(DealsAndOffersData(brandName = "Brand name", discount = "Get 10% entire menu"))
        list.add(DealsAndOffersData(brandName = "Brand name", discount = "Get 10% entire menu"))
        list.add(DealsAndOffersData(brandName = "Brand name", discount = "Get 10% entire menu"))
        list.add(DealsAndOffersData(brandName = "Brand name", discount = "Get 10% entire menu"))
        mutableDealsAndOffersList.value = list
    }

}
