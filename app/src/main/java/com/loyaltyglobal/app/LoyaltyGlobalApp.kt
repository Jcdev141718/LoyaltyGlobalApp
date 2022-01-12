package com.loyaltyglobal.app

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.google.firebase.FirebaseApp
import com.loyaltyglobal.ui.base.AppTheme
import com.loyaltyglobal.util.Constants
import com.loyaltyglobal.util.Constants.OneSignalAppId
import com.loyaltyglobal.util.PhoneAuthHelper
import com.loyaltyglobal.util.PreferenceProvider
import com.onesignal.OneSignal
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

/**
 * Created by Abhin.
 */
@HiltAndroidApp
class LoyaltyGlobalApp : Application() {

    @Inject
    lateinit var preferenceProvider: PreferenceProvider
    override fun onCreate() {
        super.onCreate()
        initTheme() //        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.getDefaultNightMode())
        FirebaseApp.initializeApp(this)
        PhoneAuthHelper.initFirebase()

        // Enable verbose OneSignal logging to debug issues if needed.
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE)

        // OneSignal Initialization
        OneSignal.initWithContext(this)
        OneSignal.setAppId(OneSignalAppId)
    }

    private fun initTheme() {
        when (preferenceProvider.getValue(Constants.PREF_APP_THEME, "")) {
            AppTheme.Dark.name -> setTheme(AppTheme.Dark)
            AppTheme.Light.name -> setTheme(AppTheme.Light)
            else -> setTheme(AppTheme.Light)
        }
    }

    private fun setTheme(theme: AppTheme) {
        when (theme) {
            AppTheme.Dark -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                preferenceProvider.setValue(Constants.PREF_APP_THEME, AppTheme.Dark.name)
            }
            AppTheme.Light -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                preferenceProvider.setValue(Constants.PREF_APP_THEME, AppTheme.Light.name)
            }
        }
    }
}