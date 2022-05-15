package com.example.appordertour.model

data class OrderTour(
    var idUser: String = "",
    var ListIdTour: ArrayList<ItemIdTour>? = null,
)

data class ItemIdTour(
    var idTour: String,
    var createAt: Long
)