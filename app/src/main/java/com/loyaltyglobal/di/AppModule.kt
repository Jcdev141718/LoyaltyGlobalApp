package com.loyaltyglobal.di

import android.content.Context
import com.loyaltyglobal.data.reposotory.AuthRepository
import com.loyaltyglobal.data.source.network.ApiService
import com.loyaltyglobal.util.PreferenceProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Abhin.
 */

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun getPreferenceManager(@ApplicationContext appContext: Context) =
        PreferenceProvider(appContext)
}
