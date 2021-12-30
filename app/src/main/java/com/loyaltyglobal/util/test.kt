package com.loyaltyglobal.util

import java.util.*

/**
 * Created by Abhin.
 */
fun main(args: Array<String>) {
    val list = arrayListOf("Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday")
    Collections.rotate(list,2)
    print(list)
}