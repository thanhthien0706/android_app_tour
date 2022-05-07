package com.example.appordertour.model

import java.io.Serializable
import java.util.*

data class Tour(
    var id: String? = null,
    var status: String? = "",
    var nameTour: String? = "",
    var location: String? = "",
    var price: Long? = 0,
    var startDate: Long? = 0,
    var endDate: Long? = 0,
    var adults: Long? = 1,
    var avater: String? = "https://firebasestorage.googleapis.com/v0/b/app-order-tour-dacs3.appspot.com/o/bg_traveljpg.jpg?alt=media&token=e89a8037-db97-4e27-a4fa-68e2b89f08ce",
    var isPrivate: Boolean? = true,
    var description: String? = "",
    var listImage: List<String>? = null,
    var stoppoint: List<TouristStopover>? = null
) : Serializable