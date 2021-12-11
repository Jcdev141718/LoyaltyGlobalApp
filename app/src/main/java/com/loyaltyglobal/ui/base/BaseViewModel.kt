package com.loyaltyglobal.ui.base

import androidx.lifecycle.ViewModel
import com.loyaltyglobal.data.source.local.DatabaseDAO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

/**
 * Created by Abhin.
 */

@HiltViewModel
open class BaseViewModel : ViewModel() {

    @Inject
    lateinit var dataBaseDao: DatabaseDAO
    val coroutineScope = Dispatchers.IO
}