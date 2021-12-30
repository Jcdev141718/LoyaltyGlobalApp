package com.loyaltyglobal.data.reposotory

import com.loyaltyglobal.data.source.local.DatabaseDAO
import com.loyaltyglobal.data.source.localModels.subBrandResponse.SubBrand
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

/**
 * Created by Abhin.
 */
@ActivityRetainedScoped
class ExploreRepository @Inject constructor(
    private val databaseDAO: DatabaseDAO,
) {

    suspend fun getAllSubBrandList(): ArrayList<SubBrand> {
        return ArrayList(databaseDAO.getAllSubBrands())
    }
}