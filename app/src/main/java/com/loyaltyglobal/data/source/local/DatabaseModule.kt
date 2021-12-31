package com.loyaltyglobal.data.source.local

import android.content.Context
import androidx.room.Room
import com.loyaltyglobal.util.Constants.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by Abhin.
 */

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context) : LocalDatabase {
        return Room.databaseBuilder(context, LocalDatabase::class.java, DATABASE_NAME).fallbackToDestructiveMigration().build()
    }


    @Provides
    fun provideDatabaseDao(appDatabase: LocalDatabase) : DatabaseDAO {
        return appDatabase.dataBaseDao()
    }
}