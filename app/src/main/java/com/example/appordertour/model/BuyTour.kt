package com.example.appordertour.model

import java.io.Serializable

data class BuyTour(
    var id: String,
    var idUser: String,
    var idTour: String,
    var statusPayment: Boolean,
    var createAt: Long
) :
    Serializable