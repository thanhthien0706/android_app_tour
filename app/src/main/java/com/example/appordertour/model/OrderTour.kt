package com.example.appordertour.model

import java.io.Serializable

data class OrderTour(
    var idUser: String = "",
    var ListIdTour: ArrayList<ItemIdTour>? = null,
)

data class ItemIdTour(
    var idTour: String,
    var statusBooking: String? = "booking",
    var createAt: Long
) : Serializable