package com.loyaltyglobal.data.source.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

/**
 * Created by Abhin.
 */
class TypeConverterHelper {

    @TypeConverter
    fun stringToArrayList(data: String): List<String>? {
        val listType: Type = object : TypeToken<List<String?>?>() {}.type
        return Gson().fromJson(data, listType)
    }

    @TypeConverter
    fun arraylistToString(someObjects: List<String>?): String {
        return Gson().toJson(someObjects)
    }

    @TypeConverter
    fun stringIntToArrayList(data: String): List<Int>? {
        val listType: Type = object : TypeToken<List<Int?>?>() {}.type
        return Gson().fromJson(data, listType)
    }

    @TypeConverter
    fun arraylistObjToString(someObjects: List<Int>?): String {
        return Gson().toJson(someObjects)
    }
}