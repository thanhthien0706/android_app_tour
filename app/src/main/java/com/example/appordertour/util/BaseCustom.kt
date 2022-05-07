package com.example.appordertour.util

import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

class BaseCustom {
    fun convertLongToTime(time: Long?): String {
        val date = Date(time!!)
        val format = SimpleDateFormat("dd.MM.yyyy")
        return format.format(date)
    }

    fun convertLongtoCurrency(currency: Long?): String {
        val format: NumberFormat = NumberFormat.getCurrencyInstance()
        format.maximumFractionDigits = 0
        format.currency = Currency.getInstance("VND")

        return format.format(currency)
    }
}