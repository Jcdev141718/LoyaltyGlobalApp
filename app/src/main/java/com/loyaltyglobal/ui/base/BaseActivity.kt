package com.loyaltyglobal.ui.base

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.loyaltyglobal.util.Constants
import com.loyaltyglobal.util.PreferenceProvider
import com.loyaltyglobal.util.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * Created by Abhin.
 */

@AndroidEntryPoint
open class BaseActivity : AppCompatActivity() {

    @Inject
    lateinit var mPreferenceProvider: PreferenceProvider

    override fun onDestroy() {
        hideKeyboard()
        super.onDestroy()
    }

    fun setTheme(theme: AppTheme) {
        when (theme) {
            AppTheme.Dark -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                mPreferenceProvider.setValue(Constants.PREF_APP_THEME, AppTheme.Dark.name)
            }
            AppTheme.Light -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                mPreferenceProvider.setValue(Constants.PREF_APP_THEME, AppTheme.Light.name)
            }
        }
    }
}

enum class AppTheme {
    Dark, Light
}
