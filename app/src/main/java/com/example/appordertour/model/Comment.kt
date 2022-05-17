package com.example.appordertour.model

data class Comment(
    var id: String? = "",
    var idUser: String? = "",
    var idTour: String? = "",
    var idParent: String? = "",
    var content: String? = "",
    var createAt: Long? = 0,
)