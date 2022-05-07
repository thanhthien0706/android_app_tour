package com.example.appordertour.model

import java.util.*

data class MessengerDetail(
    var idSender: String? = "",
    var content: String? = "",
    var createAt: Long? = null,
    var idMessenger: String? = ""
)