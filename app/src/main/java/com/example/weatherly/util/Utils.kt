package com.example.weatherly.util

import android.annotation.SuppressLint
import android.os.Parcelable
import java.text.SimpleDateFormat
import java.util.Date

@SuppressLint("SimpleDateFormat")
fun formatDate(timestamp: Int): String {
    val sdf = SimpleDateFormat("EEE, MMM d")
    val date = Date(timestamp.toLong() * 1000)
    return sdf.format(date)
}

fun fahrenheitToCelsius(temp: Double): Int {
    val celsius = ((temp-32)*5/9).toInt()
    return celsius
}

@SuppressLint("SimpleDateFormat")
fun formatTime(timestamp: Int): String {
    val sdf = SimpleDateFormat("hh:mm:aa")
    val date = Date(timestamp.toLong() * 1000)
    return sdf.format(date)
}