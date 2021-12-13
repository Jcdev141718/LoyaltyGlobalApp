package com.loyaltyglobal.app

import android.app.Application
import com.google.firebase.FirebaseApp
import com.google.firebase.ktx.Firebase
import com.loyaltyglobal.util.PhoneAuthHelper
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
    }
}
