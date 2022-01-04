package com.loyaltyglobal.data.reposotory

import com.loyaltyglobal.data.source.local.DatabaseDAO
import com.loyaltyglobal.data.source.localModels.LinkKeyValueModel
import com.loyaltyglobal.data.source.localModels.SubBrandAndCoalition
import com.loyaltyglobal.data.source.localModels.subBrandResponse.DealOffer
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

/**
 * Created by Abhin.
 */
@ActivityRetainedScoped
class ExploreRepository @Inject constructor(
    private val databaseDAO: DatabaseDAO,
) {

    suspend fun getFilters(): ArrayList<String> {
        return ArrayList(databaseDAO.getFilters())
    }

    suspend fun getDealAndOffersList(): ArrayList<DealOffer> {
        return ArrayList(databaseDAO.getDealsAndOffers().filter { !it.image.isNullOrEmpty() })
    }

    suspend fun getSubBrandWithCoalitionData(): ArrayList<SubBrandAndCoalition> {
        return ArrayList(databaseDAO.getSubBrandWithCoalitionData())
    }

    suspend fun getKeyValueData(childBrandId : String) : ArrayList<LinkKeyValueModel> {
        return ArrayList(databaseDAO.getKeyValueData(childBrandId).filter { !it.value.isNullOrEmpty() })
    }
}