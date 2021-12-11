package com.loyaltyglobal.di

import javax.inject.Qualifier

/**
 * Created by Abhin.
 */
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AuthInterceptorOkHttpClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class OtherInterceptorOkHttpClient

