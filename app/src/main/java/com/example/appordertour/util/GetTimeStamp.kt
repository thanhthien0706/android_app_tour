package com.example.appordertour.util

import android.icu.text.DateFormat.getDateTimeInstance
import java.util.*

class GetTimeStamp {
    fun getTimeDate(timestamp: Long): String? {
        return try {
            val dateFormat: android.icu.text.DateFormat? = getDateTimeInstance()
            val netDate = Date(timestamp)
            dateFormat?.format(netDate)
        } catch (e: Exception) {
            "date"
        }
    }
}