package com.figueroa.weatherapp.utils

import java.text.SimpleDateFormat
import java.util.Date

fun formatDate(timeStamp: Int): String {
    val simpleDateFormat = SimpleDateFormat("EEE, MMM d")
    val date = Date(timeStamp.toLong() * 1000)

    return simpleDateFormat.format(date)
}

fun formatDateTime(timeStamp: Int): String {
    val simpleDateFormat = SimpleDateFormat("hh:mm:aa")
    val date = Date(timeStamp.toLong() * 1000)

    return simpleDateFormat.format(date)
}

fun formatDecimals(item: Double): String {
    return " %.0f".format(item)
}