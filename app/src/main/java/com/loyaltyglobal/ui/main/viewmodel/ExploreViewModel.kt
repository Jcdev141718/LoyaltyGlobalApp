package com.loyaltyglobal.ui.main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loyaltyglobal.data.model.BusinessData
import com.loyaltyglobal.data.model.DealsAndOffersData
import com.loyaltyglobal.data.model.ExploreFilterData

/**
 * Created by Abhin.
 */
class ExploreViewModel : ViewModel() {

    var mutableBusinessList = MutableLiveData<ArrayList<BusinessData>>()
    var mutableFilterList = MutableLiveData<ArrayList<ExploreFilterData>>()
    var mutableDealsAndOffersList = MutableLiveData<ArrayList<DealsAndOffersData>>()

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

    fun getFilterList() {
        val list = ArrayList<ExploreFilterData>()
        list.add(ExploreFilterData(filter_name = "Gift Cards & Mobile Recharges"))
        list.add(ExploreFilterData(filter_name = "Beauty, Health, Grocery"))
        list.add(ExploreFilterData(filter_name = "Mobiles, Computers"))
        list.add(ExploreFilterData(filter_name = "Books"))
        list.add(ExploreFilterData(filter_name = "Movies, Music & Video Games"))
        list.add(ExploreFilterData(filter_name = "Sports, Fitness, Bags, Luggage"))
        list.add(ExploreFilterData(filter_name = "Home, Kitchen, Pets"))
        list.add(ExploreFilterData(filter_name = "Toys, Baby Products, Kids Fashion"))
        list.add(ExploreFilterData(filter_name = "Women's Fashion"))
        list.add(ExploreFilterData(filter_name = "Men's Fashion"))
        list.add(ExploreFilterData(filter_name = "Car, Motorbike, Industrial"))
        list.add(ExploreFilterData(filter_name = "Home & Garden / Major Appliance / Freezers"))
        mutableFilterList.value = list
    }
}
