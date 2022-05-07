package com.example.appordertour.model

data class TouristStopover(
    var id: String? = null,
    var nameStopover: String? = "",
    var address: String? = "",
    var serviceType: String? = "",
    var price: Long? = 0,
    var startDate: Long? = 0,
    var endDate: Long? = 0,
    var listImage: List<String>,
    var contact: String? = ""
)