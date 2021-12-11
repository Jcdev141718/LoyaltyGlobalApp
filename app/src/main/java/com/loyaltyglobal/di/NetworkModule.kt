package com.loyaltyglobal.di

import com.loyaltyglobal.BuildConfig
import com.loyaltyglobal.data.source.network.ApiService
import com.loyaltyglobal.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

/**
 * Created by Abhin.
 */
@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor? {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        return logging
    }

    @Singleton
    @Provides
    fun provideHttpClient(
        loggingInterceptor: HttpLoggingInterceptor?,
        @Named("headerIntercepter") headerInterceptor: Interceptor?,
    ): OkHttpClient {
        val okHttpClient: OkHttpClient.Builder = OkHttpClient.Builder()

        headerInterceptor?.let { interceptor ->
            okHttpClient.readTimeout(15, TimeUnit.SECONDS)
                .connectTimeout(15, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
        }
        if (BuildConfig.DEBUG) {
            // enable OkHttp logging only during debug
            loggingInterceptor?.let { okHttpClient.addInterceptor(it) }
        }
        return okHttpClient.build()
    }

    @Singleton
    @Provides
    fun provideConvertorFactory(): GsonConverterFactory =
        GsonConverterFactory.create()

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)


    @Provides @Singleton @Named("headerIntercepter")
    fun provideHeaderInterceptor(): Interceptor {
        return Interceptor { chain: Interceptor.Chain ->
            val request: Request.Builder = chain.request()
                .newBuilder()
                .header(
                    "buildNumber",
                    BuildConfig.VERSION_CODE.toString() + ""
                )
                .header(
                    "Authorization",
                    ""
                )
            chain.proceed(request.build())
        }
    }
}