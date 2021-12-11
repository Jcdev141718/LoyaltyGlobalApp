package com.loyaltyglobal.ui.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers

/**
 * Created by Abhin.
 */


open class BaseViewModel : ViewModel() {

//    lateinit var dataBaseDao: DatabaseDAO
    val coroutineScope = Dispatchers.IO
}
