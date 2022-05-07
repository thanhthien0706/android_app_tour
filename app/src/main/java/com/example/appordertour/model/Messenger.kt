package com.example.appordertour.model

import java.util.*

data class Messenger(
    var id: String,
    var idUser: String,
    var createAt: Long? = null,
)