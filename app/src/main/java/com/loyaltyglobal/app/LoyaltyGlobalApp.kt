package com.loyaltyglobal.app

import android.app.Application
import com.google.firebase.FirebaseApp
import com.loyaltyglobal.util.Constants.OneSignalAppId
import com.loyaltyglobal.util.PhoneAuthHelper
import com.onesignal.OneSignal
import dagger.hilt.android.HiltAndroidApp

/**
 * Created by Abhin.
 */
@HiltAndroidApp
class LoyaltyGlobalApp : Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        PhoneAuthHelper.initFirebase()

        // Enable verbose OneSignal logging to debug issues if needed.
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE)

        // OneSignal Initialization
        OneSignal.initWithContext(this)
        OneSignal.setAppId(OneSignalAppId)
    }
}