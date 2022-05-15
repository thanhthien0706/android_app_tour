package com.example.appordertour.model

import java.util.*

data class User(
    var id: String? = "",
    var userName: String,
    var mail: String,
    var phone: String? = "",
    var gender: String? = "",
    var permission: String? = "user",
    var avatar: String? =
        "https://firebasestorage.googleapis.com/v0/b/app-order-tour-dacs3.appspot.com/o/user_avt.png?alt=media&token=2566c32a-7a3c-42e0-b871-b9cb1faaff8b",
    var date: String = "",
    var address: String? = ""
)