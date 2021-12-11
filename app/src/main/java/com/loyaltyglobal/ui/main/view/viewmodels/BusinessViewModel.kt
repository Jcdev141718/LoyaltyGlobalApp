package com.loyaltyglobal.ui.main.view.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loyaltyglobal.data.model.BusinessData

/**
 * Created by Abhin.
 */
class BusinessViewModel : ViewModel() {

    var mutableBusinessList = MutableLiveData<ArrayList<BusinessData>>()

    fun getBusinessList() {
        val list = ArrayList<BusinessData>()
        list.add(
            BusinessData(
                brand = "Cluccboi",
                sub_title = "Fast Food",
                distance = "4 kilometer away",
                offer = "10% OFF"
            )
        )
        list.add(
            BusinessData(
                brand = "Wendy's",
                sub_title = "Fried Chicken",
                distance = "4 kilometer away",
                offer = "10% OFF"
            )
        )
        list.add(
            BusinessData(
                brand = "Burger king",
                sub_title = "Burgers",
                distance = "4 kilometer away",
                offer = "10% OFF"
            )
        )
        list.add(
            BusinessData(
                brand = "Versace",
                sub_title = "Fashion",
                distance = "4 kilometer away",
                offer = "10% OFF"
            )
        )
        list.add(
            BusinessData(
                brand = "Gucci",
                sub_title = "Fashion",
                distance = "4 kilometer away",
                offer = "10% OFF"
            )
        )
        list.add(
            BusinessData(
                brand = "Starbucks",
                sub_title = "Category",
                distance = "4 kilometer away",
                offer = "10% OFF"
            )
        )
        mutableBusinessList.value = list
    }

}
