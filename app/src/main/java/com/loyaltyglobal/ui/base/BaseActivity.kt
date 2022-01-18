package com.loyaltyglobal.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.lifecycleScope
import com.loyaltyglobal.data.source.local.DatabaseDAO
import com.loyaltyglobal.data.source.localModels.SubBrandAndCoalition
import com.loyaltyglobal.util.Constants
import com.loyaltyglobal.util.PreferenceProvider
import com.loyaltyglobal.util.createCustomMarker
import com.loyaltyglobal.util.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Created by Abhin.
 */

@AndroidEntryPoint
open class BaseActivity : AppCompatActivity() {

    @Inject
    lateinit var mPreferenceProvider: PreferenceProvider

    @Inject
    lateinit var databaseDAO: DatabaseDAO

    var mutableBusinessList : ArrayList<SubBrandAndCoalition> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getBusinessList()
    }

    override fun onDestroy() {
        hideKeyboard()
        super.onDestroy()
    }

    private fun getBusinessList() {
        lifecycleScope.launch {
            val brandList = withContext(Dispatchers.IO) {databaseDAO.getSubBrandWithCoalitionData()}
            brandList.forEach { brand ->
                val bitmap = createCustomMarker(this@BaseActivity, brand.subBrand.brandLogo.toString())
                brand.subBrand.bitmap = bitmap
                mutableBusinessList.add(brand)
            }

        }
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
