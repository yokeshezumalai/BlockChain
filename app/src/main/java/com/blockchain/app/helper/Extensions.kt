package com.blockchain.app.helper

import java.text.SimpleDateFormat
import java.util.*

fun Long.unixToMillis(): Long {
    return this * 1000
}

fun Long.toDateFormat(dateFormat: String): String {
    return SimpleDateFormat(dateFormat, Locale.getDefault()).format(Date(this))
}