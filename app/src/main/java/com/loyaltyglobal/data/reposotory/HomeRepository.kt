package com.loyaltyglobal.data.reposotory

import android.content.Context
import com.loyaltyglobal.data.source.network.ApiService
import com.loyaltyglobal.data.source.network.BaseApiResponse
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class HomeRepository @Inject constructor(
    private val apiService: ApiService, @ApplicationContext context: Context) : BaseApiResponse(context) {

}
